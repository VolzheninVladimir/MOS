package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnterSecondPlayerScreen(
    currentUsername: String,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    ScreenContainer {

        Text("Введите имя второго игрока")

        Spacer(Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = {
                name = it
                error = null
            },
            label = { Text("Имя игрока") },
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))

        WideButton("Начать игру") {
            when {
                name.isBlank() ->
                    error = "Имя не может быть пустым"

                name.trim() == currentUsername ->
                    error = "Нельзя выбрать самого себя"

                else ->
                    onConfirm(name.trim())
            }
        }

        WideButton("Отмена", onCancel)
    }
}

