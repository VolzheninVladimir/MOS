package com.example.singleactivityapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singleactivityapp.R
import com.example.singleactivityapp.ui.welcome.WelcomeFragment

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                var errorMessage by remember { mutableStateOf<String?>(null) }

                LoginScreen(
                    onRegister = { login, password ->
                        val success = viewModel.register(login, password)
                        if (success) {
                            navigateToWelcome(login)
                        } else {
                            errorMessage = "Такой логин уже существует"
                        }
                    },
                    onLogin = { login, password ->
                        val success = viewModel.login(login, password)
                        if (success) {
                            navigateToWelcome(login)
                        } else {
                            errorMessage = "Неверный логин или пароль"
                        }
                    },
                    errorMessage = errorMessage
                )
            }
        }
    }

    private fun navigateToWelcome(login: String) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                WelcomeFragment().apply {
                    arguments = bundleOf("login" to login)
                }
            )
            .addToBackStack(null)
            .commit()
    }
}
