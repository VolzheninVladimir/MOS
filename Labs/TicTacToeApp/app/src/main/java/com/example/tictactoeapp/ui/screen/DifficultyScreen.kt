package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoeapp.ui.Difficulty

interface DifficultyContract {
    fun onEasy()
    fun onMedium()
    fun onHard()
}

@Composable
fun DifficultyScreen(contract: DifficultyContract) {
    ScreenContainer {
        WideButton("Лёгкий") { contract.onEasy() }
        WideButton("Средний") { contract.onMedium() }
        WideButton("Сложный") { contract.onHard() }
    }
}

