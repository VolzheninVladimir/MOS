package com.example.recycleviewapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.recycleviewapp.data.db.AppDatabase
import com.example.recycleviewapp.data.repository.CharacterRepository
import com.example.recycleviewapp.data.network.RickAndMortyService

class CharacterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "characters.db"
            ).build()

            val dao = db.characterDao()

            val service = RickAndMortyService()

            val repository = CharacterRepository(service, dao)

            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
