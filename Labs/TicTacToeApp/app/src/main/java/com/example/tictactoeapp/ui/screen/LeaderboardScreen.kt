package com.example.tictactoeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoeapp.leaderboard.LeaderboardAdapter
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModel

@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel,
    onBack: () -> Unit
) {
    val users = viewModel.users.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            factory = { context ->
                RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = LeaderboardAdapter()

                    setPadding(0, 32, 0, 0)
                    clipToPadding = false
                }
            },
            update = { recyclerView ->
                (recyclerView.adapter as LeaderboardAdapter).submitList(users)
            }
        )

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text("Назад")
        }
    }
}
