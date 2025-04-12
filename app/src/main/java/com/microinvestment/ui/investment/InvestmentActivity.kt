package com.microinvestment.ui.investment

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microinvestment.R
import com.microinvestment.data.models.Plan
import com.microinvestment.databinding.ActivityInvestmentBinding
import com.microinvestment.utils.NotificationHelper
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.viewmodels.InvestmentViewModel
import com.microinvestment.viewmodels.PlanViewModel

class InvestmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvestmentBinding
    private lateinit var planViewModel: PlanViewModel
    private lateinit var investmentViewModel: InvestmentViewModel

    private var planData: List<Plan> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        planViewModel = ViewModelProvider(this)[PlanViewModel::class.java]
        investmentViewModel = ViewModelProvider(this)[InvestmentViewModel::class.java]

        with(planViewModel) {
            plans.observe(this@InvestmentActivity) { plans ->
                planData = plans
                val planNames = plans.map { it.name }
                with(binding) {
                    planSpinner.adapter = ArrayAdapter(
                        this@InvestmentActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        planNames
                    )
                }

            }

            loadAllPlans()
        }
    }

    private fun initViews() {
        binding.apply {
            header.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            header.title.text = getString(R.string.investments)

            investButton.setOnClickListener {
                val selectedPlan = planData[planSpinner.selectedItemPosition]
                val amount = amountEditText.text.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    investmentViewModel.addInvestment(
                        SharedPrefManager(this@InvestmentActivity).getUserId(),
                        selectedPlan.id,
                        amount
                    )

                    Toast.makeText(
                        this@InvestmentActivity, "Investment Successful", Toast.LENGTH_SHORT
                    ).show()
                    NotificationHelper.sendNotification(
                        this@InvestmentActivity,
                        "Investment",
                        "Invested UGX $amount in ${selectedPlan.name}"
                    )
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    Toast.makeText(this@InvestmentActivity, "Invalid amount", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}