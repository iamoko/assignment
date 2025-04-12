package com.microinvestment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.InvestmentSummary
import com.microinvestment.data.models.InvestmentWithPlan
import com.microinvestment.data.repository.InvestmentRepository
import com.microinvestment.data.repository.PlanRepository
import com.microinvestment.utils.InvestmentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InvestmentViewModel(application: Application) : AndroidViewModel(application) {
    private val investmentRepository: InvestmentRepository =
        InvestmentRepository(application)
    private val planRepository: PlanRepository =
        PlanRepository(application)
    val investments = MutableLiveData<List<Investment>>()
    val investmentCreationStatus = MutableLiveData<Boolean>()

    // Get all investments for a user
    fun getUserInvestments(userId: Int) {
        viewModelScope.launch {
            val userInvestments = investmentRepository.getUserInvestments(userId)
            investments.postValue(userInvestments)
        }
    }

    // Create a new investment
    fun addInvestment(userId: Int, planId: Int, amount: Double) {
        viewModelScope.launch {
            val investment = Investment(
                userId = userId,
                planId = planId,
                amount = amount,
                startDate = System.currentTimeMillis(),
                withdrawnAt = null
            )
            investmentRepository.createInvestment(investment)
            investmentCreationStatus.postValue(true)  // Notify that investment was created
        }
    }

    // Update the status of an investment (e.g., after withdrawal)
    fun updateInvestment(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.updateInvestment(investment)
        }
    }

    private val _investmentWithPlan = MutableLiveData<InvestmentWithPlan?>()
    val investmentWithPlan: LiveData<InvestmentWithPlan?> = _investmentWithPlan

    fun loadInvestmentWithPlan(investmentId: Int) {
        viewModelScope.launch {
            _investmentWithPlan.value = investmentRepository.getInvestmentWithPlan(investmentId)
        }
    }

    /** Get all investment with plan by user */
    private val _userInvestments = MutableLiveData<List<InvestmentWithPlan>>()
    val userInvestments: LiveData<List<InvestmentWithPlan>> = _userInvestments

    fun loadUserInvestments(userId: Int) {
        viewModelScope.launch {
            _userInvestments.value = investmentRepository.getInvestmentsWithPlansByUser(userId)
        }
    }

    private val _withdrawals = MutableLiveData<List<InvestmentWithPlan>>()
    val withdrawals: LiveData<List<InvestmentWithPlan>> get() = _withdrawals

    fun loadWithdrawals(userId: Int) {
        viewModelScope.launch {
            _withdrawals.value = investmentRepository.getWithdrawalsByUser(userId)
        }
    }


    /** Plan details */
    val investmentSummary = MutableLiveData<InvestmentSummary>()
    fun loadSummary(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val investments = investmentRepository.getUserInvestments(userId)

            var invested = 0.0
            var earnings = 0.0
            var withdrawable = 0.0

            investments.forEach {
                val plan = planRepository.getPlanById(it.planId)
                val currentValue = InvestmentUtils.calculateCurrentValue(it, plan)
                invested += it.amount
                earnings += (currentValue - it.amount)
                if (InvestmentUtils.canWithdraw(it, plan)) withdrawable += currentValue
            }

            investmentSummary.postValue(
                InvestmentSummary(invested, earnings, withdrawable)
            )
        }
    }
}