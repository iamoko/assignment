package com.microinvestment.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.microinvestment.MainActivity
import com.microinvestment.R
import com.microinvestment.databinding.ActivityWelcomeBinding
import com.microinvestment.utils.SharedPrefManager

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var sharedPref: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.getStarted.setOnClickListener {
            startActivity(
                Intent(
                    this, LoginActivity::class.java
                )
            )
        }

        sharedPref = SharedPrefManager(this)
        if (sharedPref.isLoggedIn()) {
            startActivity(
                Intent(
                    this, MainActivity::class.java
                )
            )
            return
        }
    }
}