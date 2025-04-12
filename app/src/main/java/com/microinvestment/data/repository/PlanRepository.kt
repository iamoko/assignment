package com.microinvestment.data.repository

import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Plan


class PlanRepository(private val db: AppDatabase) {

    fun getAllPlans(): List<Plan> {
        return db.planDao().getAll()
    }

    fun getPlanById(id: Int): Plan {
        return db.planDao().getById(id)
    }

    fun insertPlan(plan: Plan) {
        db.planDao().insert(plan)
    }
}
