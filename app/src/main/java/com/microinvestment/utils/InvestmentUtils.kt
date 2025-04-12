package com.microinvestment.utils

import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan

object InvestmentUtils {
    fun calculateCurrentValue(investment: Investment, plan: Plan): Double {
        val daysElapsed =
            (System.currentTimeMillis() - investment.startDate) / (1000 * 60 * 60 * 24)
        return investment.amount * Math.pow(1 + plan.returnRate / 100, daysElapsed.toDouble())
    }

    fun canWithdraw(investment: Investment, plan: Plan): Boolean {
        val daysElapsed =
            (System.currentTimeMillis() - investment.startDate) / (1000 * 60 * 60 * 24)
        return daysElapsed >= plan.lockPeriodDays && !investment.isWithdrawn
    }
}