package com.example.tictactoeapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Диалог завершения игры.
 * Показывает победителя или ничью.
 */
@Composable
fun ResultDialogComposable(
    winner: Char?,
    isDraw: Boolean,
    onRestart: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val message = when {
        isDraw -> "Ничья!"
        winner != null -> "Победитель: $winner"
        else -> ""
    }

    if (message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { /* запрет закрытия без кнопок */ },
            title = { Text(text = "Игра завершена") },
            text = { Text(text = message) },
            confirmButton = {
                Row {
                    TextButton(onClick = onRestart) {
                        Text("Начать заново")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onBackToMenu) {
                        Text("В меню")
                    }
                }
            }
        )
    }
}

