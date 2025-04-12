package com.microinvestment.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.microinvestment.ui.dashboard.HomeFragment
import com.microinvestment.ui.dashboard.InvestmentFragment


@Suppress("DEPRECATION")
class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager) {
    var list = ArrayList<String>()
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> InvestmentFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

}