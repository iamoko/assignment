package com.microinvestment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.microinvestment.utils.Utils

@Entity
data class Investment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val planId: Int,
    val amount: Double,
    val startDate: Long, // timestamp
    var isWithdrawn: Boolean = false,
    var createdAt: String = Utils.currentDate()
)
