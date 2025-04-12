package com.microinvestment.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.microinvestment.databinding.FragmentInvestmentBinding


class InvestmentFragment : Fragment() {
    private lateinit var binding: FragmentInvestmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvestmentBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = InvestmentFragment().apply {}
    }
}