package com.example.tictactoeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoeapp.App
import com.example.tictactoeapp.ai.AiStrategy
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy
import com.example.tictactoeapp.auth.AuthManagerImpl
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.database.UserEntity
import com.example.tictactoeapp.leaderboard.LeaderboardRepository
import com.example.tictactoeapp.settings.SettingsRepository
import com.example.tictactoeapp.settings.SettingsViewModel
import com.example.tictactoeapp.settings.SettingsViewModelFactory
import com.example.tictactoeapp.ui.screen.*
import com.example.tictactoeapp.ui.viewmodel.GameViewModelFactory
import com.example.tictactoeapp.ui.viewmodel.GameViewModelImpl
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModel
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModelFactory

sealed class Screen {
    object Menu : Screen()
    object PlayModeSelect : Screen()
    object ChooseSide : Screen()
    data class GameHumanTraining(val userPlaysAs: Char) : Screen()
    data class GameHumanCompetitive(
        val opponentName: String,
        val userPlaysAs: Char
    ) : Screen()
    data class GameComputer(
        val difficulty: Difficulty,
        val userPlaysAs: Char
    ) : Screen()
    data class DifficultySelect(val userPlaysAs: Char) : Screen()
    data class EnterSecondPlayer(val userPlaysAs: Char) : Screen()
    object Settings : Screen()
    object Statistics : Screen()
    object Leaderboard : Screen()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = (application as App).db
        val authManager = AuthManagerImpl(
            context = applicationContext,
            userDao = db.userDao()
        )

        setContent {
            val currentUser by authManager.currentUser.collectAsState()

            var currentScreen by remember { mutableStateOf<Screen>(Screen.Menu) }

            var pendingLogin by remember { mutableStateOf<String?>(null) }

            var previousAction by remember { mutableStateOf("") }

            if (pendingLogin != null) {
                LaunchedEffect(pendingLogin) {
                    authManager.login(pendingLogin!!)
                    pendingLogin = null
                }
            }

            var pendingLogout by remember { mutableStateOf(false) }

            if (pendingLogout) {
                LaunchedEffect(Unit) {
                    authManager.logout()
                    pendingLogout = false
                }
            }

            if (currentUser == null) {
                LoginScreen(
                    contract = object : LoginContract {
                        override fun onLogin(username: String) {
                            pendingLogin = username
                        }
                    }
                )

                return@setContent
            }

            when (val screen = currentScreen) {

                Screen.Menu -> MenuScreen(
                    contract = object : MenuContract {
                        override fun onPlay() {
                            currentScreen = Screen.PlayModeSelect
                        }

                        override fun onSettings() {
                            currentScreen = Screen.Settings
                        }

                        override fun onLogout() {
                            pendingLogout = true
                        }

                        override fun onStatistics() {
                            currentScreen = Screen.Statistics
                        }

                        override fun onLeaderboard() {
                            currentScreen = Screen.Leaderboard
                        }
                    },
                    username = currentUser!!.username
                )

                Screen.PlayModeSelect -> {
                    PlayModeSelectScreen(
                        onTraining = {
                            previousAction = "training"
                            currentScreen = Screen.ChooseSide
                        },
                        onCompetitive = {
                            previousAction = "competitive"
                            currentScreen = Screen.ChooseSide
                        },
                        onComputer = {
                            previousAction = "computer"
                            currentScreen = Screen.ChooseSide
                        },
                        onBack = { currentScreen = Screen.Menu }
                    )
                }

                Screen.ChooseSide -> {
                    ChooseSideScreen(
                        onChoose = { side ->
                            when (previousAction) {
                                "training" -> currentScreen = Screen.GameHumanTraining(side)
                                "competitive" -> currentScreen = Screen.EnterSecondPlayer(side)
                                "computer" -> currentScreen = Screen.DifficultySelect(side)
                            }
                        },
                        onCancel = { currentScreen = Screen.Menu }
                    )
                }

                is Screen.DifficultySelect -> DifficultyScreen(
                    contract = object : DifficultyContract {
                        override fun onEasy() {
                            currentScreen = Screen.GameComputer(Difficulty.EASY, userPlaysAs = screen.userPlaysAs)
                        }
                        override fun onMedium() {
                            currentScreen = Screen.GameComputer(Difficulty.MEDIUM, userPlaysAs = screen.userPlaysAs)
                        }
                        override fun onHard() {
                            currentScreen = Screen.GameComputer(Difficulty.HARD, userPlaysAs = screen.userPlaysAs)
                        }
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
                        onBackToMenu = { currentScreen = Screen.Menu }
                    )
                }

                is Screen.EnterSecondPlayer -> {
                    EnterSecondPlayerScreen(
                        currentUsername = currentUser!!.username,
                        onConfirm = { opponentName ->
                            currentScreen = Screen.GameHumanCompetitive(opponentName, screen.userPlaysAs)
                        },
                        onCancel = { currentScreen = Screen.Menu }
                    )
                }

                is Screen.GameHumanTraining -> {
                    val viewModel: GameViewModelImpl = viewModel(factory = GameViewModelFactory())

                    LaunchedEffect(screen) {
                        viewModel.startNewGame(
                            playerX = HumanPlayer('X'),
                            playerO = HumanPlayer('O')
                        )
                    }

                    // Тренировка — статистики нет

                    TicTacToeScreen(
                        viewModel = viewModel,
                        onBackToMenu = { currentScreen = Screen.Menu }
                    )
                }


                is Screen.GameHumanCompetitive -> {
                    val db = (application as App).db
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
                        onBackToMenu = { currentScreen = Screen.Menu }
                    )
                }


                Screen.Statistics -> {
                    StatisticsScreen(
                        userFlow = authManager.currentUser,
                        onBack = { currentScreen = Screen.Menu }
                    )
                }

                Screen.Leaderboard -> {
                    val db = (application as App).db
                    val repository = remember { LeaderboardRepository(db.userDao()) }

                    val viewModel: LeaderboardViewModel = viewModel(
                        factory = LeaderboardViewModelFactory(repository)
                    )

                    LeaderboardScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = Screen.Menu }
                    )
                }

                Screen.Settings -> {
                    val viewModel: SettingsViewModel = viewModel(
                        factory = SettingsViewModelFactory(SettingsRepository(db.userDao()))
                    )

                    SettingsScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = Screen.Menu }
                    )
                }

            }
        }
    }
}
