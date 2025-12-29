package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PlayModeSelectScreen(
    onTraining: () -> Unit,
    onCompetitive: () -> Unit,
    onComputer: () -> Unit,
    onBack: () -> Unit
) {
    ScreenContainer {
        WideButton("Тренировка", onTraining)
        WideButton("Соревновательный режим", onCompetitive)
        WideButton("Играть с компьютером", onComputer)
        WideButton("Назад", onBack)
    }
}
