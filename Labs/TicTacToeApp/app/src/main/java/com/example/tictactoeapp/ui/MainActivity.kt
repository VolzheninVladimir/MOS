package com.example.tictactoeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoeapp.ui.screen.MenuContract
import com.example.tictactoeapp.ui.screen.MenuScreen
import com.example.tictactoeapp.ui.screen.TicTacToeScreen

sealed class Screen {
    object Menu : Screen()
    object GameHuman : Screen()
    object GameComputer : Screen()
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
            val viewModel: GameViewModelImpl = viewModel()

            when (currentScreen) {
                Screen.Menu -> MenuScreen(object : MenuContract {
                    override fun onPlayHuman() { currentScreen = Screen.GameHuman }
                    override fun onPlayComputer() { currentScreen = Screen.GameComputer }
                    override fun onSettings() { /* TODO */ }
                })

                Screen.GameHuman -> TicTacToeScreen(
                    viewModel = viewModel,
                    onBackToMenu = { currentScreen = Screen.Menu }
                )

                Screen.GameComputer -> TicTacToeScreen(
                    viewModel = viewModel,
                    onBackToMenu = { currentScreen = Screen.Menu }
                )
            }
        }
    }
}

