package com.microinvestment.data.repository

import android.content.Context
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.InvestmentWithPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class InvestmentRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    suspend fun getUserInvestments(userId: Int): List<Investment> {
        return db.investmentDao().getUserInvestments(userId)
    }

    suspend fun createInvestment(investment: Investment) {
        db.investmentDao().insert(investment)
    }

    suspend fun updateInvestment(investment: Investment) {
        db.investmentDao().update(investment)
    }

    suspend fun getInvestmentWithPlan(id: Int): InvestmentWithPlan? = withContext(Dispatchers.IO) {
        db.investmentDao().getInvestmentWithPlan(id)
    }

    suspend fun getInvestmentsWithPlansByUser(userId: Int): List<InvestmentWithPlan> =
        withContext(Dispatchers.IO) {
            db.investmentDao().getAllInvestmentsWithPlans(userId)
        }

    suspend fun getWithdrawalsByUser(userId: Int): List<InvestmentWithPlan> =
        withContext(Dispatchers.IO) {
            db.investmentDao().getWithdrawalsWithPlans(userId)
        }
}
