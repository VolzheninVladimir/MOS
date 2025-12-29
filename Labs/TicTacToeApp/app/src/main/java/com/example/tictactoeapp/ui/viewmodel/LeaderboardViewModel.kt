package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoeapp.auth.User
import com.example.tictactoeapp.leaderboard.LeaderboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val repository: LeaderboardRepository
) : ViewModel() {

    val users: StateFlow<List<User>> =
        repository.loadLeaderboard()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

}
