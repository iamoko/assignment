package com.microinvestment.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.microinvestment.R
import com.microinvestment.databinding.ActivityRegisterBinding
import com.microinvestment.utils.NotificationHelper
import com.microinvestment.utils.Utils.makeLinks
import com.microinvestment.viewmodels.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        initViews()
    }

    private fun viewModel() {
        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        // Observe the registration status
        authViewModel.registrationStatus.observe(this@RegisterActivity) { user ->
            if (user != null) {
                NotificationHelper.sendNotification(
                    this@RegisterActivity,
                    getString(R.string.registration_successful),
                    "You have Successfully created an account"
                )
                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()
                // Navigate to login or next screen
                goToLogin()
            } else {
                Toast.makeText(
                    this@RegisterActivity, "Username already taken", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun initViews() {
        binding.apply {
            /** Password toggle */
            textInput.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            registerButton.setOnClickListener {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val name = nameEditText.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Username and Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                // Call ViewModel to handle registration
                authViewModel.register(name, username, password)
            }

            loginButton.makeLinks(
                Pair(getString(R.string.login_here), View.OnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }),
            )
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }
}