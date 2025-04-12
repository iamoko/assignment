package com.microinvestment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microinvestment.data.models.InvestmentWithPlan
import com.microinvestment.databinding.LayoutInvestmentBinding
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.Utils


class WithdrawAdapter(private var data: List<InvestmentWithPlan>) :
    RecyclerView.Adapter<WithdrawAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutInvestmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WithdrawAdapter.ViewHolder {
        val binding =
            LayoutInvestmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WithdrawAdapter.ViewHolder, position: Int) {
        with(holder) {
            val investment = data[position].investment
            val plan = data[position].plan

            val currentValue = InvestmentUtils.calculateCurrentValue(
                investment,
                plan
            )
            val canWithdraw =
                InvestmentUtils.canWithdraw(investment, plan)

            with(binding) {
                planText.text = "Plan: ${plan.name}"
                investedText.text = "Invested: UGX ${Utils.numFormat(investment.amount)}"
                currentValueText.text = "Current Value: UGX ${Utils.numFormat(currentValue)}"
                statusText.text =
                    "Status: ${if (investment.isWithdrawn) "Withdrawn" else if (canWithdraw) "Ready to Withdraw" else "Locked"}"
                withdrawButton.visibility = View.GONE

            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
