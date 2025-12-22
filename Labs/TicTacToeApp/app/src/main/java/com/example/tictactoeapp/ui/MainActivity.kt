package com.example.tictactoeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoeapp.ui.screen.DifficultyContract
import com.example.tictactoeapp.ui.screen.DifficultyScreen
import com.example.tictactoeapp.ui.screen.MenuContract
import com.example.tictactoeapp.ui.screen.MenuScreen
import com.example.tictactoeapp.ui.screen.TicTacToeScreen
import com.example.tictactoeapp.ui.viewmodel.GameViewModelFactory
import com.example.tictactoeapp.ui.viewmodel.GameViewModelImpl

sealed class Screen {
    object Menu : Screen()
    object GameHuman : Screen()
    data class GameComputer(val difficulty: Difficulty) : Screen()
    object DifficultySelect : Screen()
}



/**
 * Точка входа приложения.
 * Управляет переключением между меню и экраном игры.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var currentScreen by remember { mutableStateOf<Screen>(Screen.Menu) }

            when (val screen = currentScreen) {

                Screen.Menu -> MenuScreen(
                    object : MenuContract {
                        override fun onPlayHuman() { currentScreen = Screen.GameHuman }
                        override fun onPlayComputer() { currentScreen = Screen.DifficultySelect }
                        override fun onSettings() {}
                    }
                )

                Screen.DifficultySelect -> DifficultyScreen(
                    object : DifficultyContract {
                        override fun onEasy() { currentScreen = Screen.GameComputer(Difficulty.EASY) }
                        override fun onMedium() { currentScreen = Screen.GameComputer(Difficulty.MEDIUM) }
                        override fun onHard() { currentScreen = Screen.GameComputer(Difficulty.HARD) }
                    }
                )

                Screen.GameHuman -> {
                    val viewModel: GameViewModelImpl = viewModel(
                        factory = GameViewModelFactory(
                            isComputerGame = false,
                            difficulty = null
                        )
                    )
                    TicTacToeScreen(
                        viewModel = viewModel,
                        onBackToMenu = { currentScreen = Screen.Menu },
                        isComputerGame = false
                    )
                }

                is Screen.GameComputer -> {
                    val viewModel: GameViewModelImpl = viewModel(
                        factory = GameViewModelFactory(
                            isComputerGame = true,
                            difficulty = screen.difficulty
                        )
                    )
                    TicTacToeScreen(
                        viewModel = viewModel,
                        onBackToMenu = { currentScreen = Screen.Menu },
                        isComputerGame = true
                    )
                }
            }
        }
    }
}


