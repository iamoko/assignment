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


class WithdrawalsAdapter(private var data: List<InvestmentWithPlan>) :
    RecyclerView.Adapter<WithdrawalsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutInvestmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WithdrawalsAdapter.ViewHolder {
        val binding =
            LayoutInvestmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WithdrawalsAdapter.ViewHolder, position: Int) {
        with(holder) {
            val investment = data[position].investment
            val plan = data[position].plan

            val currentValue = InvestmentUtils.calculateCurrentValue(
                investment,
                plan
            )

            with(binding) {
                planText.text = "Plan: ${plan.name}"
                investedText.text = "Original Amount: UGX ${Utils.numFormat(investment.amount)}"
                currentValueText.text =
                    "Current Value (with interest): UGX ${Utils.numFormat(currentValue)}"
                statusText.visibility = View.GONE
                withdrawButton.visibility = View.GONE

            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
