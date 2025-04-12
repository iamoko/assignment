package com.microinvestment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val returnRate: Double,
    val lockPeriodDays: Int
)