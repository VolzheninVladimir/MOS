package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppTitleBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                start = 12.dp,
                end = 12.dp
            )
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "TicTacToe",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}
