package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChooseSideScreen(
    onChoose: (Char) -> Unit,
    onCancel: () -> Unit
) {
    ScreenContainer {

        Text("Выберите, за кого играть")

        Spacer(Modifier.height(24.dp))

        WideButton("Играть за X") { onChoose('X') }
        WideButton("Играть за O") { onChoose('O') }

        Spacer(Modifier.height(24.dp))

        WideButton("Отмена", onCancel)
    }
}
