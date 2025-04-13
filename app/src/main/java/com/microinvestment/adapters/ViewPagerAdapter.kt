package com.microinvestment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.microinvestment.ui.dashboard.HomeFragment
import com.microinvestment.ui.dashboard.InvestmentFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity, private var userID: Int) :
    FragmentStateAdapter(fragmentActivity) {
    var list = ArrayList<String>()
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> InvestmentFragment.newInstance(list[position], userID)
            else -> InvestmentFragment.newInstance(list[position], userID)
        }
    }
}
