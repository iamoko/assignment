package com.microinvestment.data.repository

import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User


class AuthRepository(private val db: AppDatabase) {

    fun getUser(username: String): User? {
        return db.userDao().getUser(username)
    }

    fun getUserById(id: Int): User? {
        return db.userDao().getUserById(id)
    }

    fun registerUser(user: User) = db.userDao().insert(user)


    fun login(username: String, password: String): User? {
        return db.userDao().login(username, password)
    }
}
