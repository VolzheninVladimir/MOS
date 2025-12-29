package com.example.tictactoeapp.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}
