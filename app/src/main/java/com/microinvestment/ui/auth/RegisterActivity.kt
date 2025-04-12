package com.microinvestment.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microinvestment.databinding.ActivityRegisterBinding
import com.microinvestment.viewmodels.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }


    private fun initViews() {
        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.apply {

            // Observe the registration status
            authViewModel.registrationStatus.observe(this@RegisterActivity) { isRegistered ->
                if (isRegistered) {
                    Toast.makeText(
                        this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to login or next screen
                    goToLogin()
                } else {
                    Toast.makeText(
                        this@RegisterActivity, "Username already taken", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            registerButton.setOnClickListener {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Username and Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // Call ViewModel to handle registration
                authViewModel.register(username, password)
            }

            loginTextView.setOnClickListener {
                // Navigate to Login screen
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }
}