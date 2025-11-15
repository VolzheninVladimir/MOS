package com.example.singleactivityapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onRegister: (String, String) -> Unit,
    onLogin: (String, String) -> Unit,
    errorMessage: String?
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onRegister(login, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Регистрация")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { onLogin(login, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вход")
        }

        if (errorMessage != null) {
            Spacer(Modifier.height(16.dp))
            Text(errorMessage)
        }
    }
}
