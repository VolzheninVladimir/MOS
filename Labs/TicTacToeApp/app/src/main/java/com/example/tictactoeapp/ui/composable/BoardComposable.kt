package com.example.tictactoeapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Отображает игровое поле 3×3.
 * При клике по клетке вызывает callback.
 */
@Composable
fun BoardComposable(
    board: Array<Array<Char?>>,
    onCellClick: (row: Int, col: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { onCellClick(row, col) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val value = board[row][col]

                        when (value) {
                            'X' -> Text(
                                text = "✕",
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                                color = MaterialTheme.colorScheme.primary
                            )
                            'O' -> Text(
                                text = "◯",
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            else -> Text("")
                        }
                    }
                }
            }
        }
    }
}

