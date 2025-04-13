package com.microinvestment

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microinvestment.data.dao.InvestmentDao
import com.microinvestment.data.dao.PlanDao
import com.microinvestment.data.dao.UserDao
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan
import com.microinvestment.data.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var planDao: PlanDao
    private lateinit var investmentDao: InvestmentDao

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = db.userDao()
        planDao = db.planDao()
        investmentDao = db.investmentDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun testUserRegistrationAndLogin() = runTest {
        val user = User(name = "Amoko Ivan", username = "testuser", password = "test123")
        val userId = userDao.insert(user)

        val loggedInUser = userDao.login("testuser", "test123")

        assertNotNull(loggedInUser)
        assertEquals(userId, loggedInUser?.id)
    }

    @Test
    fun testAddAndFetchInvestment() = runTest {
        val planId = planDao.insert(Plan(name = "Standard", returnRate = 1.5, lockPeriodDays = 1))
        val userId =
            userDao.insert(User(name = "Amoko", username = "investor", password = "123456"))

        val investment = Investment(
            userId = userId.toInt(),
            planId = planId.toInt(),
            amount = 5000.0,
            startDate = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000)
        )
        investmentDao.insert(investment)

        val investments = investmentDao.getUserInvestments(userId.toInt())
        assertTrue(investments.isNotEmpty())
        assertEquals(5000.0, investments[0].amount, 0.0)
    }
}

// Include all test cases below here
