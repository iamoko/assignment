package com.microinvestment.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.microinvestment.R
import com.microinvestment.adapters.HomeAdapter
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.Home
import com.microinvestment.data.models.InvestmentWithPlan
import com.microinvestment.data.models.User
import com.microinvestment.databinding.FragmentHomeBinding
import com.microinvestment.ui.investment.InvestmentActivity
import com.microinvestment.utils.Clicked
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.NotificationHelper
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.viewmodels.InvestmentViewModel
import com.microinvestment.viewmodels.PlanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPref: SharedPrefManager


    var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)


        sharedPref = SharedPrefManager(requireContext())
        userId = sharedPref.getUserId()
        initViews()

        return binding.root
    }

    private fun initViews() {

        /** To avoid blocking the main thread */
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserById(userId)
            }
            if (user != null) loadData(user)
        }
    }

    private fun loadData(user: User) {
        binding.apply {
            name.text = user.name
            with(recyclerView) {
                isNestedScrollingEnabled = false
                layoutManager = (LinearLayoutManager(requireContext()))
                adapter = HomeAdapter(listOf(
                    Home(
                        getString(R.string.invest_now), ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_investment
                        )!!
                    ),
                    Home(
                        getString(R.string.invest_now), ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_investment
                        )!!
                    ),
                    Home(
                        getString(R.string.invest_now), ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_investment
                        )!!
                    ),
                ), object : Clicked {
                    override fun onClick(label: String) {
                        when (label) {
                            getString(R.string.invest_now) -> startActivity(
                                Intent(
                                    requireContext(), InvestmentActivity::class.java
                                )
                            )
                        }
                    }

                    override fun onClick(data: InvestmentWithPlan) {

                    }

                })
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply { }
    }
}