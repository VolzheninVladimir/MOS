package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoeapp.ui.BoardComposable
import com.example.tictactoeapp.ui.GameViewModel
import com.example.tictactoeapp.ui.ResultDialogComposable

/**
 * Главный экран игры.
 * Добавлена кнопка "Назад в меню".
 */
@Composable
fun TicTacToeScreen(
    viewModel: GameViewModel,
    onBackToMenu: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoardComposable(
            board = state.board,
            onCellClick = { row, col ->
                viewModel.makeMove(row, col)
            }
        )

        if (state.isGameOver) {
            ResultDialogComposable(
                winner = state.winner,
                isDraw = state.isDraw,
                onRestart = { viewModel.resetGame() },
                onBackToMenu = onBackToMenu
            )
        }
    }
}
