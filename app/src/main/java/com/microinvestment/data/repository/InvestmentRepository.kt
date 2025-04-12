package com.microinvestment.data.repository

import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.User


class InvestmentRepository(private val db: AppDatabase) {

    // User
    fun getUser(username: String) = db.userDao().getUser(username)
    fun registerUser(user: User) = db.userDao().insert(user)
    fun getUserById(id: Int) = db.userDao().getUserById(id)

    // Plans
    fun getAllPlans() = db.planDao().getAll()
    fun getPlanById(id: Int) = db.planDao().getById(id)

    // Investments
    fun invest(investment: Investment) = db.investmentDao().insert(investment)
    fun getUserInvestments(userId: Int) = db.investmentDao().getUserInvestments(userId)
    fun updateInvestment(investment: Investment) = db.investmentDao().update(investment)
}
