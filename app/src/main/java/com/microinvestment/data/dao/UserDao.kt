package com.microinvestment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.microinvestment.data.models.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User): Long

    @Query("SELECT * FROM User WHERE username = :username")
    fun getUser(username: String): User?

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User?  // Add this method to get user by ID
}