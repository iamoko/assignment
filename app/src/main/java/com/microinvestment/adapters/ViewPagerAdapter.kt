package com.microinvestment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.microinvestment.ui.dashboard.HomeFragment
import com.microinvestment.ui.dashboard.InvestmentFragment


@Suppress("DEPRECATION")
class ViewPagerAdapter(supportFragmentManager: FragmentManager, private var userID: Int) :
    FragmentPagerAdapter(supportFragmentManager) {
    var list = ArrayList<String>()
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> InvestmentFragment.newInstance(list[position], userID)
            else -> InvestmentFragment.newInstance(list[position], userID)
        }
    }

}