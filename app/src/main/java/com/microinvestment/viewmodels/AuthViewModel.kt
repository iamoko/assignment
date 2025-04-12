package com.microinvestment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User
import kotlinx.coroutines.launch


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)

    // LiveData to observe registration and login statuses
    val loginStatus = MutableLiveData<User?>()
    val registrationStatus = MutableLiveData<Boolean>()

    // Login function
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = db.userDao().getUser(username)
            if (user != null && user.password == password) {
                // Valid user, login successful
                loginStatus.postValue(user)
            } else {
                // Invalid credentials, login failed
                loginStatus.postValue(null)
            }
        }
    }

    // Register function
    fun register(username: String, password: String) {
        viewModelScope.launch {
            // Check if user already exists
            if (db.userDao().getUser(username) != null) {
                registrationStatus.postValue(false) // User already exists
            } else {
                // Register new user
                val user = User(username = username, password = password)
                db.userDao().insert(user)
                registrationStatus.postValue(true) // Successfully registered
            }
        }
    }
}
