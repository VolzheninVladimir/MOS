package com.example.singleactivityapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.singleactivityapp.data.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun register(login: String, password: String): Boolean {
        val ok = repository.register(login, password)
        if (ok) repository.setCurrentLogin(login)
        return ok
    }

    fun login(login: String, password: String): Boolean {
        val ok = repository.authenticate(login, password)
        if (ok) repository.setCurrentLogin(login)
        return ok
    }

    fun getCurrentLogin(): String? = repository.getCurrentLogin()

    fun logout() {
        repository.clearCurrentLogin()
    }
}
