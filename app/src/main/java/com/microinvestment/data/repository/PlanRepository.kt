package com.microinvestment.data.repository

import android.content.Context
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Plan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PlanRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)

    // Fetch all plans
    suspend fun getAllPlans(): List<Plan> = withContext(Dispatchers.IO) {
        db.planDao().getAllPlans()
    }

    // Fetch a specific plan by ID
    suspend fun getPlanById(id: Int): Plan = withContext(Dispatchers.IO) {
        db.planDao().getPlanById(id)
    }
}
