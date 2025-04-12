package com.microinvestment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan
import kotlinx.coroutines.launch


class InvestmentViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val plans = MutableLiveData<List<Plan>>()
    val investments = MutableLiveData<List<Investment>>()

    fun loadPlans() = viewModelScope.launch {
        plans.postValue(db.planDao().getAll())
    }

    fun invest(userId: Int, planId: Int, amount: Double) = viewModelScope.launch {
        val now = System.currentTimeMillis()
        val investment =
            Investment(
                userId = userId,
                planId = planId,
                amount = amount,
                startDate = now,
                withdrawnAt = null
            )
        db.investmentDao().insert(investment)
        loadUserInvestments(userId)
    }

    fun loadUserInvestments(userId: Int) = viewModelScope.launch {
        investments.postValue(db.investmentDao().getUserInvestments(userId))
    }

    fun withdrawInvestment(investment: Investment, plan: Plan) = viewModelScope.launch {
        val now = System.currentTimeMillis()
        val daysPassed = (now - investment.startDate) / (1000 * 60 * 60 * 24)
        if (daysPassed >= plan.lockPeriodDays) {
            db.investmentDao()
                .update(investment.copy(isWithdrawn = true, withdrawnAt = now))
            loadUserInvestments(investment.userId)
        }
    }
}