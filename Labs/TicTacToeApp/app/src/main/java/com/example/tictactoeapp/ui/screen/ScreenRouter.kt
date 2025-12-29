package com.example.tictactoeapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoeapp.App
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.auth.AuthManager
import com.example.tictactoeapp.auth.User
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.database.AppDatabase
import com.example.tictactoeapp.database.UserEntity
import com.example.tictactoeapp.leaderboard.LeaderboardRepository
import com.example.tictactoeapp.settings.SettingsRepository
import com.example.tictactoeapp.settings.SettingsViewModel
import com.example.tictactoeapp.settings.SettingsViewModelFactory
import com.example.tictactoeapp.ui.Difficulty
import com.example.tictactoeapp.ui.screen.Screen
import com.example.tictactoeapp.ui.strategyFor
import com.example.tictactoeapp.ui.viewmodel.GameViewModelFactory
import com.example.tictactoeapp.ui.viewmodel.GameViewModelImpl
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModel
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModelFactory
import kotlin.let

@Composable
fun ScreenRouter(
    screen: Screen,
    currentUser: User,
    db: AppDatabase,
    authManager: AuthManager,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit
) {
    when (screen) {

        Screen.Menu -> MenuScreen(
            contract = object : MenuContract {
                override fun onPlay() = onNavigate(Screen.PlayModeSelect)
                override fun onSettings() = onNavigate(Screen.Settings)
                override fun onLogout() = onLogout()
                override fun onStatistics() = onNavigate(Screen.Statistics)
                override fun onLeaderboard() = onNavigate(Screen.Leaderboard)
            },
            username = currentUser.username
        )

        Screen.PlayModeSelect -> PlayModeSelectScreen(
            onTraining = { onNavigate(Screen.ChooseSide("training")) },
            onCompetitive = { onNavigate(Screen.ChooseSide("competitive")) },
            onComputer = { onNavigate(Screen.ChooseSide("computer")) },
            onBack = { onNavigate(Screen.Menu) }
        )

        is Screen.ChooseSide -> ChooseSideScreen(
            onChoose = { side ->
                when (screen.mode) {
                    "training" -> onNavigate(Screen.GameHumanTraining(side))
                    "competitive" -> onNavigate(Screen.EnterSecondPlayer(side))
                    "computer" -> onNavigate(Screen.DifficultySelect(side))
                }
            },
            onCancel = { onNavigate(Screen.Menu) }
        )

        is Screen.DifficultySelect -> DifficultyScreen(
            contract = object : DifficultyContract {
                override fun onEasy() = onNavigate(Screen.GameComputer(Difficulty.EASY, screen.userPlaysAs))
                override fun onMedium() = onNavigate(Screen.GameComputer(Difficulty.MEDIUM, screen.userPlaysAs))
                override fun onHard() = onNavigate(Screen.GameComputer(Difficulty.HARD, screen.userPlaysAs))
            }
        )

        is Screen.GameComputer -> {
            val viewModel: GameViewModelImpl = viewModel(factory = GameViewModelFactory())

            LaunchedEffect(screen) {
                viewModel.startNewGame(
                    playerX = if (screen.userPlaysAs == 'X')
                        HumanPlayer('X')
                    else
                        ComputerPlayer('X', "Computer", strategyFor(screen.difficulty)),
                    playerO = if (screen.userPlaysAs == 'O')
                        HumanPlayer('O')
                    else
                        ComputerPlayer('O', "Computer", strategyFor(screen.difficulty))
                )
            }

            TicTacToeScreen(
                viewModel = viewModel,
                onBackToMenu = { onNavigate(Screen.Menu) }
            )
        }

        is Screen.EnterSecondPlayer -> EnterSecondPlayerScreen(
            currentUsername = currentUser.username,
            onConfirm = { opponentName ->
                onNavigate(Screen.GameHumanCompetitive(opponentName, screen.userPlaysAs))
            },
            onCancel = { onNavigate(Screen.Menu) }
        )

        is Screen.GameHumanTraining -> {
            val viewModel: GameViewModelImpl = viewModel(factory = GameViewModelFactory())

            LaunchedEffect(screen) {
                viewModel.startNewGame(
                    playerX = HumanPlayer('X'),
                    playerO = HumanPlayer('O')
                )
            }

            TicTacToeScreen(
                viewModel = viewModel,
                onBackToMenu = { onNavigate(Screen.Menu) }
            )
        }

        is Screen.GameHumanCompetitive -> {
            val dao = db.userDao()
            val viewModel: GameViewModelImpl = viewModel(factory = GameViewModelFactory())

            LaunchedEffect(screen) {
                viewModel.startNewGame(
                    playerX = HumanPlayer('X'),
                    playerO = HumanPlayer('O')
                )
            }

            LaunchedEffect(screen) {
                viewModel.gameFinished.collect { winner ->
                    val resultForUser = when (winner) {
                        screen.userPlaysAs -> 'X'
                        'D' -> 'D'
                        else -> 'O'
                    }

                    authManager.updateStats(resultForUser)

                    val opponentName = screen.opponentName
                    val opponent = dao.getUserByName(opponentName)
                        ?: let {
                            val id = dao.insertUser(UserEntity(username = opponentName)).toInt()
                            dao.getUserById(id)
                        }!!

                    val resultForOpponent = when (resultForUser) {
                        'X' -> 'O'
                        'O' -> 'X'
                        else -> 'D'
                    }

                    val updatedOpponent = when (resultForOpponent) {
                        'X' -> opponent.copy(wins = opponent.wins + 1)
                        'O' -> opponent.copy(losses = opponent.losses + 1)
                        else -> opponent.copy(draws = opponent.draws + 1)
                    }

                    dao.updateUser(updatedOpponent)
                }
            }

            TicTacToeScreen(
                viewModel = viewModel,
                onBackToMenu = { onNavigate(Screen.Menu) }
            )
        }

        Screen.Statistics -> StatisticsScreen(
            userFlow = authManager.currentUser,
            onBack = { onNavigate(Screen.Menu) }
        )

        Screen.Leaderboard -> {
            val repository = remember { LeaderboardRepository(db.userDao()) }
            val viewModel: LeaderboardViewModel = viewModel(
                factory = LeaderboardViewModelFactory(repository)
            )

            LeaderboardScreen(
                viewModel = viewModel,
                onBack = { onNavigate(Screen.Menu) }
            )
        }

        Screen.Settings -> {
            val viewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(SettingsRepository(db.userDao()))
            )

            SettingsScreen(
                viewModel = viewModel,
                onBack = { onNavigate(Screen.Menu) }
            )
        }
    }
}
