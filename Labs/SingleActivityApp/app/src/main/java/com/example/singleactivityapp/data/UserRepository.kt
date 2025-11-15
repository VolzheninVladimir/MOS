package com.example.singleactivityapp.data

import android.content.Context

class UserRepository(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun register(login: String, password: String): Boolean {
        val existingPassword = prefs.getString(login, null)
        return if (existingPassword == null) {
            prefs.edit().putString(login, password).apply()
            true
        } else {
            false
        }
    }

    fun authenticate(login: String, password: String): Boolean {
        val savedPassword = prefs.getString(login, null)
        return savedPassword != null && savedPassword == password
    }

    fun setCurrentLogin(login: String) {
        prefs.edit().putString("current_login", login).apply()
    }

    fun getCurrentLogin(): String? {
        return prefs.getString("current_login", null)
    }

    fun clearCurrentLogin() {
        prefs.edit().remove("current_login").apply()
    }
}
