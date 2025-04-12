package com.microinvestment.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val returnRate: Double,
    val lockPeriodDays: Int
)

data class InvestmentWithPlan(
    @Embedded val investment: Investment,
    @Relation(
        parentColumn = "planId",
        entityColumn = "id"
    )
    val plan: Plan
)
