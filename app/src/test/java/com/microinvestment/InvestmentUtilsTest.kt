package com.microinvestment

import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan
import com.microinvestment.utils.InvestmentUtils
import org.junit.Assert.*
import org.junit.Test

class InvestmentUtilsTest {

    @Test
    fun `calculateCurrentValue returns correct value`() {
        val investment = Investment(
            id = 1,
            userId = 1,
            planId = 1,
            amount = 1000.0,
            startDate = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000),
            withdrawnAt = null
        )
        val plan = Plan(id = 1, name = "Test Plan", returnRate = 1.5, lockPeriodDays = 7)
        val value = InvestmentUtils.calculateCurrentValue(investment, plan)

        assertTrue(value > 1000.0)
    }

    @Test
    fun `canWithdraw returns false before lock period`() {
        val investment = Investment(
            id = 1,
            userId = 1,
            planId = 1,
            amount = 1000.0,
            startDate = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000),
            isWithdrawn = false,
            withdrawnAt = null
        )
        val plan = Plan(id = 1, name = "Test Plan", returnRate = 1.5, lockPeriodDays = 7)

        assertFalse(InvestmentUtils.canWithdraw(investment, plan))
    }

    @Test
    fun `canWithdraw returns true after lock period`() {
        val investment = Investment(
            id = 1,
            userId = 1,
            planId = 1,
            amount = 1000.0,
            startDate = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000),
            isWithdrawn = false,
            withdrawnAt = null
        )
        val plan = Plan(id = 1, name = "Test Plan", returnRate = 1.5, lockPeriodDays = 7)

        assertTrue(InvestmentUtils.canWithdraw(investment, plan))
    }

    @Test
    fun testCanWithdrawTrue() {
        val investment = Investment(
            userId = 1,
            planId = 1,
            amount = 1000.0,
            startDate = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000L), // 10 days ago
            isWithdrawn = false, withdrawnAt = null
        )
        val plan = Plan(id = 1, name = "Test Plan", returnRate = 2.0, lockPeriodDays = 7)

        val result = InvestmentUtils.canWithdraw(investment, plan)
        assertTrue(result)
    }

    @Test
    fun testCanWithdrawFalse() {
        val investment = Investment(
            userId = 1,
            planId = 1,
            amount = 1000.0,
            startDate = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000L), // 5 days ago
            isWithdrawn = false, withdrawnAt = null
        )
        val plan = Plan(id = 1, name = "Test Plan", returnRate = 2.0, lockPeriodDays = 7)

        val result = InvestmentUtils.canWithdraw(investment, plan)
        assertFalse(result)
    }
}
