package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ui.composable.BoardComposable
import com.example.tictactoeapp.ui.composable.ResultDialogComposable
import com.example.tictactoeapp.ui.viewmodel.GameViewModel

@Composable
fun TicTacToeScreen(
    viewModel: GameViewModel,
    onBackToMenu: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.currentPlayer) {
        if (!state.isGameOver) {
            val player = viewModel.currentPlayerObject()
            if (player is ComputerPlayer) {
                viewModel.makeAIMove()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        AppTitleBar()

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackToMenu) {
                Text("Назад")
            }
            Button(onClick = { viewModel.resetGame() }) {
                Text("Заново")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                    onBackToMenu = {
                        viewModel.resetGame()
                        onBackToMenu()
                    }
                )
            }
        }
    }
}
