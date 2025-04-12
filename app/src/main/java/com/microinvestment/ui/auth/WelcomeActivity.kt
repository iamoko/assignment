package com.microinvestment.ui.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.microinvestment.MainActivity
import com.microinvestment.R
import com.microinvestment.databinding.ActivityWelcomeBinding
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.utils.Utils

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var sharedPref: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        askNotificationPermission()


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

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {/*Reuse.getFCMToken(this, viewModel)*/
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Utils.showAlert(
                    getString(R.string.please_accept_app_permissions), this
                )
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {/* Reuse.getFCMToken(this, viewModel)*/
        }
    }

    /* Declare the launcher at the top of your Activity/Fragment: */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {/*Reuse.getFCMToken(this, viewModel)*/
        } else {
            Utils.showAlert(getString(R.string.please_accept_app_permissions), this)
        }
    }
}