package com.example.tictactoeapp.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoeapp.auth.User
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("ContextCastToActivity")
@Composable
fun StatisticsScreen(
    userFlow: StateFlow<User?>,
    onBack: () -> Unit
) {
    val user = userFlow.collectAsState().value ?: return
    val activity = LocalContext.current as Activity

    AppTitleBar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text = "Статистика игрока",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = user.username,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )


                StatRow(label = "Победы", value = user.wins)
                StatRow(label = "Поражения", value = user.losses)
                StatRow(label = "Ничьи", value = user.draws)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Назад")
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 18.sp)
            Text(
                value.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

