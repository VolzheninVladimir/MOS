package com.example.tictactoeapp.auth

import androidx.datastore.preferences.core.intPreferencesKey

object AuthPreferences {
    val CURRENT_USER_ID = intPreferencesKey("current_user_id")
}
