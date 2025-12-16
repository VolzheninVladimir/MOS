package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


interface MenuContract {
    fun onPlayHuman()
    fun onPlayComputer()
    fun onSettings()
}

/**
 * Экран меню: выбор режима игры.
 */
@Composable
fun MenuScreen(contract: MenuContract) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { contract.onPlayHuman() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Играть против человека")
        }

        Button(
            onClick = { contract.onPlayComputer() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Играть против компьютера")
        }

        Button(
            onClick = { contract.onSettings() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Настройки")
        }
    }
}
