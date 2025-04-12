package com.microinvestment.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.microinvestment.R
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.databinding.ActivityPortfolioBinding
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.NotificationHelper

class PortfolioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPortfolioBinding
    private lateinit var db: AppDatabase
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }


    @SuppressLint("SetTextI18n")
    private fun initViews() {
        db = AppDatabase.getInstance(this)

        binding.apply {
//            val container = findViewById<LinearLayout>(R.id.portfolioContainer)
//            val investments = db.investmentDao().getUserInvestments(userId)
//
//            investments.forEach { inv ->
//                val plan = db.planDao().getById(inv.planId)
//                val currentValue = InvestmentUtils.calculateCurrentValue(inv, plan)
//                val eligible = InvestmentUtils.canWithdraw(inv, plan)
//
//                val text = TextView(this@PortfolioActivity)
//                text.text =
//                    "Plan: ${plan.name}\nAmount: UGX ${inv.amount}\nCurrent: UGX %.2f\nEligible: $eligible".format(
//                        currentValue
//                    )
//                container.addView(text)
//
//                if (eligible) {
//                    val withdrawButton = Button(this@PortfolioActivity).apply {
//                        text.text = "Withdraw"
//                        setOnClickListener {
//                            inv.isWithdrawn = true
//                            db.investmentDao().update(inv)
//                            NotificationHelper.sendNotification(
//                                this@PortfolioActivity,
//                                "Withdrawal",
//                                "Withdrew UGX %.2f from ${plan.name}".format(currentValue)
//                            )
//                            Toast.makeText(
//                                this@PortfolioActivity, "Withdrawal Complete", Toast.LENGTH_SHORT
//                            ).show()
//                            it.isEnabled = false
//                        }
//                    }
//                    container.addView(withdrawButton)
//                }
//            }
        }

    }

    fun setUserId(id: Int) {
        userId = id
    }
}