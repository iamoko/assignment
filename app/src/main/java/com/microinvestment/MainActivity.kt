package com.microinvestment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User
import com.microinvestment.databinding.ActivityMainBinding
import com.microinvestment.ui.auth.LoginActivity
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.viewmodels.AuthViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId != -1) {
            user = db.userDao().getUserById(userId)!!
            // Proceed to load the user's portfolio or dashboard
            loadUserPortfolio(user)
        } else {
            // Handle case when there is no valid user ID
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }, 0 * 1000)

        }
    }

    private fun loadUserPortfolio(user: User) {
        // Display the user's investment portfolio
        val portfolioTextView = findViewById<TextView>(R.id.portfolioTextView)
        val investments = db.investmentDao().getUserInvestments(user.id)

        if (investments.isEmpty()) {
            portfolioTextView.text = "You have no investments yet."
        } else {
            val portfolioContent = StringBuilder("Your Investments:\n")
            investments.forEach { investment ->
                val plan = db.planDao().getById(investment.planId)
                val currentValue = InvestmentUtils.calculateCurrentValue(investment, plan)
                portfolioContent.append(
                    "Plan: ${plan.name}\nAmount: UGX ${investment.amount}\nCurrent Value: UGX ${
                        "%.2f".format(
                            currentValue
                        )
                    }\n\n"
                )
            }
            portfolioTextView.text = portfolioContent.toString()
        }

        // You could also add buttons or other UI elements to handle further actions such as investment, withdrawal, etc.
    }
}