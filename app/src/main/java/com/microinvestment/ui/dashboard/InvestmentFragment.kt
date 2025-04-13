package com.microinvestment.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.microinvestment.R
import com.microinvestment.adapters.InvestmentAdapter
import com.microinvestment.adapters.WithdrawalsAdapter
import com.microinvestment.data.models.InvestmentWithPlan
import com.microinvestment.databinding.FragmentInvestmentBinding
import com.microinvestment.utils.Clicked
import com.microinvestment.utils.InvestmentUtils
import com.microinvestment.utils.NotificationHelper
import com.microinvestment.viewmodels.InvestmentViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "userID"

class InvestmentFragment : Fragment() {
    private var param1: String? = null
    private var userID: Int? = null
    private lateinit var investmentViewModel: InvestmentViewModel

    private lateinit var binding: FragmentInvestmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            userID = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvestmentBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {

        binding.apply {
            title.text = param1
            recyclerView.layoutManager = (LinearLayoutManager(requireContext()))
        }
        loadData()
    }

    fun loadData() {
        investmentViewModel = ViewModelProvider(this)[InvestmentViewModel::class.java]
        binding.noData.visibility = View.VISIBLE
        if (param1 == getString(R.string.investments)) investmentViewModel()
        else withdrawViewModel()
    }

    private fun withdrawViewModel() {
        with(investmentViewModel) {
            withdrawals.observe(viewLifecycleOwner) { data ->
                with(binding) {
                    noData.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
                    recyclerView.adapter = WithdrawalsAdapter(data.reversed())
                }
            }

            loadWithdrawals(userID!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun investmentViewModel() {
        with(investmentViewModel) {
            userInvestments.observe(viewLifecycleOwner) { data ->
                with(binding) {
                    noData.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
                    recyclerView.adapter = InvestmentAdapter(data.reversed(), object : Clicked {
                        override fun onClick(label: String) {}
                        override fun onClick(data: InvestmentWithPlan) {
                            val investment = data.investment
                            val plan = data.plan

                            val currentValue = InvestmentUtils.calculateCurrentValue(
                                investment, plan
                            )
                            investment.isWithdrawn = true

                            /** Indicate withdraw completed */
                            investmentViewModel.updateInvestment(investment)
                            NotificationHelper.sendNotification(
                                requireContext(),
                                "Withdrawal Successful",
                                "You have withdrawn UGX ${"%.2f".format(currentValue)} from ${plan.name}"
                            )
                            android.widget.Toast.makeText(
                                requireContext(),
                                "Withdrawal Complete",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()

                            /** Reload all data */
                            loadData()
                        }

                    })
                }

            }
            loadUserInvestments(userID!!)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, userID: Int) = InvestmentFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putInt(ARG_PARAM2, userID)
            }
        }
    }
}