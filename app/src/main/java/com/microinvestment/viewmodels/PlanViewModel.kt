package com.microinvestment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.microinvestment.data.models.Plan
import com.microinvestment.data.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlanRepository(application)

    private val _plans = MutableLiveData<List<Plan>>()
    val plans: LiveData<List<Plan>> get() = _plans

    // Fetch all plans
    fun loadAllPlans() {
        viewModelScope.launch {
            val result = repository.getAllPlans()
            _plans.postValue(result)
        }
    }
}
