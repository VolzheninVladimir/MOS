package com.example.singleactivityapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.singleactivityapp.ui.login.LoginFragment
import com.example.singleactivityapp.ui.login.LoginViewModel
import com.example.singleactivityapp.ui.welcome.WelcomeFragment

class MainActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val current = viewModel.getCurrentLogin()
            val startFragment = if (current != null) {
                WelcomeFragment().apply {
                    arguments = Bundle().apply { putString("login", current) }
                }
            } else {
                LoginFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, startFragment)
                .commit()
        }
    }
}
