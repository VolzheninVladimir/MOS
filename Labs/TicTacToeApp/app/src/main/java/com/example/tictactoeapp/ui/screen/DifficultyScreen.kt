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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { contract.onEasy() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) { Text("Лёгкий") }

        Button(
            onClick = { contract.onMedium() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) { Text("Средний") }

        Button(
            onClick = { contract.onHard() },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) { Text("Сложный") }
    }
}
