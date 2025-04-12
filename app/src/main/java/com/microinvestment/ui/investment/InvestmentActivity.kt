package com.microinvestment.ui.investment

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.microinvestment.R
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.databinding.ActivityInvestmentBinding
import com.microinvestment.utils.NotificationHelper

class InvestmentActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private var userId: Int = -1

    private lateinit var binding: ActivityInvestmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        db = AppDatabase.getDatabase(this)

        binding.apply {
            val plans = db.planDao().getAll()
            val planNames = plans.map { it.name }
            planSpinner.adapter = ArrayAdapter(
                this@InvestmentActivity, android.R.layout.simple_spinner_item, planNames
            )

            investButton.setOnClickListener {
                val selectedPlan = plans[planSpinner.selectedItemPosition]
                val amount = amountEditText.text.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    val investment = Investment(
                        userId = userId,
                        planId = selectedPlan.id,
                        amount = amount,
                        startDate = System.currentTimeMillis(),
                        withdrawnAt = null
                    )
                    db.investmentDao().insert(investment)
                    Toast.makeText(
                        this@InvestmentActivity, "Investment Successful", Toast.LENGTH_SHORT
                    ).show()
                    NotificationHelper.sendNotification(
                        this@InvestmentActivity,
                        "Investment",
                        "Invested UGX $amount in ${selectedPlan.name}"
                    )
                } else {
                    Toast.makeText(this@InvestmentActivity, "Invalid amount", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }

    fun setUserId(id: Int) {
        userId = id
    }
}