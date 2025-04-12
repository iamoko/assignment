package com.microinvestment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.microinvestment.data.models.Plan

@Dao
interface PlanDao {
    @Insert
    fun insert(plan: Plan)

    @Query("SELECT * FROM Plan")
    suspend fun getAllPlans(): List<Plan>

    @Query("SELECT * FROM Plan WHERE id = :id")
    suspend fun getPlanById(id: Int): Plan
}