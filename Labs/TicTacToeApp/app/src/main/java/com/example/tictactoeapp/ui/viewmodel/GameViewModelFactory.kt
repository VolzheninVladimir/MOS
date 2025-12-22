package com.example.tictactoeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoeapp.ui.Difficulty

class GameViewModelFactory(
    private val isComputerGame: Boolean,
    private val difficulty: Difficulty?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModelImpl::class.java)) {
            return GameViewModelImpl(isComputerGame, difficulty) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
