package com.example.tictactoeapp.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


interface LoginContract {
    fun onLogin(username: String)
}

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginScreen(contract: LoginContract) {
    var username by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity

    AppTitleBar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Введите имя") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isNotBlank()) {
                        contract.onLogin(username.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Войти")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { activity.finishAffinity() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выйти")
            }
        }
    }
}
