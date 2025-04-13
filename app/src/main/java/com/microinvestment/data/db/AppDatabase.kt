package com.microinvestment.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.microinvestment.data.dao.InvestmentDao
import com.microinvestment.data.dao.PlanDao
import com.microinvestment.data.dao.UserDao
import com.microinvestment.data.models.Investment
import com.microinvestment.data.models.Plan
import com.microinvestment.data.models.User

@Database(
    entities = [User::class, Plan::class, Investment::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun planDao(): PlanDao
    abstract fun investmentDao(): InvestmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "investment_db"
            )
                .addCallback(seedDatabaseCallback(context))
                .build()

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        val yourDao = getInstance(context).planDao()
                        yourDao.insert(
                            Plan(
                                name = "Save Daily",
                                returnRate = 1.5,
                                lockPeriodDays = 7
                            )
                        )
                        yourDao.insert(
                            Plan(
                                name = "Grow Fast",
                                returnRate = 2.0,
                                lockPeriodDays = 14
                            )
                        )
                    }
                }
            }
        }
    }
}