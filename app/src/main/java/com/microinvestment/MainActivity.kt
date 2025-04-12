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
import androidx.lifecycle.lifecycleScope
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User
import com.microinvestment.databinding.ActivityMainBinding
import com.microinvestment.ui.auth.LoginActivity
import com.microinvestment.ui.auth.WelcomeActivity
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.viewmodels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPrefManager
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPrefManager(this)
        userId = sharedPref.getUserId()


        sharedPref = SharedPrefManager(this)
        userId = sharedPref.getUserId()

        /** To avoid blocking the main thread */
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserById(userId)
            }

            if (user != null) loadUserPortfolio(user)
        }

    }

    private fun loadUserPortfolio(user: User) {
        // Display the user's investment portfolio
        val portfolioTextView = findViewById<TextView>(R.id.portfolioTextView)
//        val investments = db.investmentDao().getUserInvestments(user.id)
//
//        if (investments.isEmpty()) {
//            portfolioTextView.text = "You have no investments yet."
//        } else {
//            val portfolioContent = StringBuilder("Your Investments:\n")
//            investments.forEach { investment ->
//                val plan = db.planDao().getById(investment.planId)
//                val currentValue = InvestmentUtils.calculateCurrentValue(investment, plan)
//                portfolioContent.append(
//                    "Plan: ${plan.name}\nAmount: UGX ${investment.amount}\nCurrent Value: UGX ${
//                        "%.2f".format(
//                            currentValue
//                        )
//                    }\n\n"
//                )
//            }
//            portfolioTextView.text = portfolioContent.toString()
//        }

        // You could also add buttons or other UI elements to handle further actions such as investment, withdrawal, etc.
    }
}