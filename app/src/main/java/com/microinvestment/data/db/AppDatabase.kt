package com.microinvestment.data.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan
import com.microinvestment.data.models.User
import java.util.concurrent.Executors


@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    fun getUser(username: String): User?

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User?  // Add this method to get user by ID
}

@Dao
interface PlanDao {
    @Insert
    fun insert(plan: Plan)

    @Query("SELECT * FROM Plan")
    fun getAll(): List<Plan>

    @Query("SELECT * FROM Plan WHERE id = :id")
    fun getById(id: Int): Plan
}

@Dao
interface InvestmentDao {
    @Insert
    fun insert(investment: Investment)

    @Query("SELECT * FROM Investment WHERE userId = :userId")
    fun getUserInvestments(userId: Int): List<Investment>

    @Query("SELECT * FROM Investment WHERE id = :id")
    fun getById(id: Int): Investment

    @Update
    fun update(investment: Investment)
}


@Database(entities = [User::class, Plan::class, Investment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun planDao(): PlanDao
    abstract fun investmentDao(): InvestmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "investment_db"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            getDatabase(context).planDao().apply {
                                insert(
                                    Plan(
                                        name = "SaveDaily",
                                        returnRate = 1.5,
                                        lockPeriodDays = 7
                                    )
                                )
                                insert(
                                    Plan(
                                        name = "GrowFast",
                                        returnRate = 2.0,
                                        lockPeriodDays = 14
                                    )
                                )
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}