package com.example.tictactoeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoeapp.App
import com.example.tictactoeapp.ai.AiStrategy
import com.example.tictactoeapp.ai.ComputerPlayer
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy
import com.example.tictactoeapp.auth.AuthManagerImpl
import com.example.tictactoeapp.core.HumanPlayer
import com.example.tictactoeapp.database.UserEntity
import com.example.tictactoeapp.leaderboard.LeaderboardRepository
import com.example.tictactoeapp.settings.SettingsRepository
import com.example.tictactoeapp.settings.SettingsViewModel
import com.example.tictactoeapp.settings.SettingsViewModelFactory
import com.example.tictactoeapp.ui.screen.*
import com.example.tictactoeapp.ui.viewmodel.GameViewModelFactory
import com.example.tictactoeapp.ui.viewmodel.GameViewModelImpl
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModel
import com.example.tictactoeapp.ui.viewmodel.LeaderboardViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = (application as App).db
        val authManager = AuthManagerImpl(
            context = applicationContext,
            userDao = db.userDao()
        )

        setContent {
            val currentUser by authManager.currentUser.collectAsState()

            var currentScreen by remember { mutableStateOf<Screen>(Screen.Menu) }

            var pendingLogin by remember { mutableStateOf<String?>(null) }

            var previousAction by remember { mutableStateOf("") }

            if (pendingLogin != null) {
                LaunchedEffect(pendingLogin) {
                    authManager.login(pendingLogin!!)
                    pendingLogin = null
                }
            }

            var pendingLogout by remember { mutableStateOf(false) }

            if (pendingLogout) {
                LaunchedEffect(Unit) {
                    authManager.logout()
                    pendingLogout = false
                }
            }

            if (currentUser == null) {
                LoginScreen(
                    contract = object : LoginContract {
                        override fun onLogin(username: String) {
                            pendingLogin = username
                        }
                    }
                )

                return@setContent
            }

            ScreenRouter(
                screen = currentScreen,
                currentUser = currentUser!!,
                db = db,
                authManager = authManager,
                onNavigate = {
                    currentScreen = it
                             },
                onLogout = {
                    pendingLogout = true
                } )

        }
    }
}
