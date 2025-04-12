package com.microinvestment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.InvestmentWithPlan


@Dao
interface InvestmentDao {
    @Insert
    suspend fun insert(investment: Investment)

    @Query("SELECT * FROM Investment WHERE userId = :userId")
    suspend fun getUserInvestments(userId: Int): List<Investment>

    @Query("SELECT * FROM Investment WHERE id = :id")
    suspend fun getById(id: Int): Investment

    @Update
    suspend fun update(investment: Investment)

    @Transaction
    @Query("SELECT * FROM Investment WHERE id = :investmentId")
    suspend fun getInvestmentWithPlan(investmentId: Int): InvestmentWithPlan?

    @Transaction
    @Query("SELECT * FROM Investment WHERE userId = :userId")
    suspend fun getAllInvestmentsWithPlans(userId: Int): List<InvestmentWithPlan>

    @Transaction
    @Query("SELECT * FROM Investment WHERE userId = :userId AND isWithdrawn = 1")
    suspend fun getWithdrawalsWithPlans(userId: Int): List<InvestmentWithPlan>
}
