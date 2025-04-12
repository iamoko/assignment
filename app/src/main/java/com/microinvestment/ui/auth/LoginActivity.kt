package com.microinvestment.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microinvestment.MainActivity
import com.microinvestment.R
import com.microinvestment.databinding.ActivityLoginBinding
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.utils.Utils.makeLinks
import com.microinvestment.viewmodels.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPref: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        initViews()
    }

    private fun viewModel() {
        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        // Observe login status
        authViewModel.loginStatus.observe(this@LoginActivity) { user ->
            if (user != null) {

                // Login successful, navigate to the main screen
                sharedPref.saveUserId(user.id)
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                // Login failed
                Toast.makeText(
                    this@LoginActivity,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initViews() {
        sharedPref = SharedPrefManager(this)
        binding.apply {

            // Login button click handler
            loginButton.setOnClickListener {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username and Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // Call ViewModel to handle login
                authViewModel.login(username, password)
            }

            registerButton.makeLinks(
                Pair(getString(R.string.register), View.OnClickListener {
                    startActivity(
                        Intent(
                            this@LoginActivity, RegisterActivity::class.java
                        )
                    )
                }),
            )
        }
    }
}