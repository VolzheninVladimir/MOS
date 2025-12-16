package com.example.recycleviewapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recycleviewapp.data.repository.CharacterRepository
import com.example.recycleviewapp.domain.RecyclerExampleData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters: StateFlow<List<RecyclerExampleData>> =
        repository.characters.map { list ->
            listOf(RecyclerExampleData.Title("Персонажи")) +
                    list.map { RecyclerExampleData.Item(it) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refresh()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
