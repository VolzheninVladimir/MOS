package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

interface MenuContract {
    fun onPlay()
    fun onSettings()
    fun onLogout()
    fun onStatistics()
    fun onLeaderboard()
}

@Composable
fun MenuScreen(
    contract: MenuContract,
    username: String
) {
    ScreenContainer {

        Text(
            text = "Привет, $username!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        MenuButton("Играть") { contract.onPlay() }
        MenuButton("Настройки") { contract.onSettings() }
        MenuButton("Статистика") { contract.onStatistics() }
        MenuButton("Лидерборд") { contract.onLeaderboard() }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { contract.onLogout() },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Выйти")
        }
    }
}


@Composable
private fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(vertical = 8.dp)
    ) {
        Text(text, fontSize = 18.sp)
    }
}
