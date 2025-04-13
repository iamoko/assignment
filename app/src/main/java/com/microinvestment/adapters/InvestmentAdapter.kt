package com.microinvestment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microinvestment.data.models.InvestmentWithPlan
import com.microinvestment.databinding.LayoutInvestmentBinding
import com.microinvestment.utils.Clicked
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.Utils


class InvestmentAdapter(
    private var data: List<InvestmentWithPlan>,
    private var listener: Clicked
) :
    RecyclerView.Adapter<InvestmentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutInvestmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): InvestmentAdapter.ViewHolder {
        val binding =
            LayoutInvestmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: InvestmentAdapter.ViewHolder, position: Int) {
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

                dateText.text = "Invested on: ${investment.createdAt}"
                withdrawButton.visibility =
                    if (canWithdraw && !investment.isWithdrawn) View.VISIBLE else View.GONE

                if (canWithdraw) {
                    withdrawButton.setOnClickListener {
                        listener.onClick(data[position])
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
