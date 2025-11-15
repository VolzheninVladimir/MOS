package com.example.singleactivityapp.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.singleactivityapp.R
import com.example.singleactivityapp.ui.login.LoginViewModel

class WelcomeFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val login = arguments?.getString("login") ?: "Гость"
        tvWelcome.text = "Добро пожаловать, $login"

        btnLogout.setOnClickListener {
            viewModel.logout()
            parentFragmentManager.popBackStack()
        }
    }
}
