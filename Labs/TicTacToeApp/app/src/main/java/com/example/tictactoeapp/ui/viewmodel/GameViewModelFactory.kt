package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoeapp.ui.Difficulty

class GameViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModelImpl::class.java)) {
            return GameViewModelImpl() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

