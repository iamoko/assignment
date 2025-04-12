package com.microinvestment.utils

import android.content.Context

class SharedPrefManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {
        prefs.edit().putInt("USER_ID", userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt("USER_ID", -1)
    }

    fun clearUser() {
        prefs.edit().remove("USER_ID").apply()
    }

    fun isLoggedIn(): Boolean {
        return getUserId() != -1
    }
}
