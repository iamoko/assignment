package com.microinvestment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User
import com.microinvestment.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = AuthRepository(db)

    private val _loginStatus = MutableLiveData<User?>()
    val loginStatus: LiveData<User?> = _loginStatus

    private val _registrationStatus = MutableLiveData<User?>()
    val registrationStatus: LiveData<User?> = _registrationStatus

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.login(username, password)
            _loginStatus.postValue(user)
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getUser(username)
            if (existing == null) {
                val userId =
                    repository.registerUser(User(username = username, password = password)).toInt()
                val newUser = repository.getUserById(userId)
                _registrationStatus.postValue(newUser)
            } else {
                _registrationStatus.postValue(null) // Already exists
            }
        }
    }
}
