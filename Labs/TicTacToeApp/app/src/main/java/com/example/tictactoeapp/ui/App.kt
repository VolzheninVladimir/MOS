package com.example.tictactoeapp

import android.app.Application
import androidx.room.Room
import com.example.tictactoeapp.database.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tictactoe.db"
        ).build()
    }
}
