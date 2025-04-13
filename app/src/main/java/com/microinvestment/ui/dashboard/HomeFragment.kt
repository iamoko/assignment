package com.microinvestment.ui.dashboard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.microinvestment.ui.auth.WelcomeActivity
import com.microinvestment.ui.investment.InvestmentActivity
import com.microinvestment.utils.Clicked
import com.microinvestment.utils.SharedPrefManager
import com.microinvestment.utils.Utils
import com.microinvestment.viewmodels.InvestmentViewModel
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

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        with(ViewModelProvider(this)[InvestmentViewModel::class.java]) {
            loadSummary(userId)
            investmentSummary.observe(viewLifecycleOwner) { summary ->

                binding.apply {
                    summaryText.text =
                        "Total Invested: UGX ${Utils.numFormat(summary.totalInvested)}"
                    earningsText.text =
                        "Total Earnings: UGX ${Utils.numFormat(summary.totalEarnings)}"
                    availableText.text =
                        "Available for Withdrawal: UGX ${Utils.numFormat(summary.available)}"
                }
            }
        }

        /** To avoid blocking the main thread */
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserById(userId)
            }
            if (user != null) loadData(user)
        }
    }

    override fun onResume() {
        super.onResume()
        ViewModelProvider(this)[InvestmentViewModel::class.java].loadSummary(userId)
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
                        getString(R.string.logout), ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_logout
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

                            getString(R.string.logout) -> logout()
                        }
                    }

                    override fun onClick(data: InvestmentWithPlan) {

                    }

                })
            }
        }
    }

    private fun logout() {
        val items = arrayOf(
            resources.getString(R.string.yes),
            resources.getString(R.string.no),
        )

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.are_you_sure_you_want_to_logout))
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == resources.getString(R.string.yes) -> {
                    with(activity) {
                        sharedPref.clearUser()
                        startActivity(Intent(this, WelcomeActivity::class.java))
                        requireActivity().finish()
                    }
                }

                items[item] == resources.getString(R.string.cancel) -> dialog.dismiss()
            }
        }
        builder.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply { }
    }
}