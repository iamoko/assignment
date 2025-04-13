package com.microinvestment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.microinvestment.adapters.ViewPagerAdapter
import com.microinvestment.databinding.ActivityMainBinding
import com.microinvestment.utils.SharedPrefManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPrefManager
    var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }


    private fun initViews() {
        sharedPref = SharedPrefManager(this)
        userId = sharedPref.getUserId()

        binding.apply {
            menu.selectedItemId = R.id.home
            viewPager()
            menu.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> binding.viewPager.currentItem = 0
                    R.id.investments -> binding.viewPager.currentItem = 1
                    R.id.withdraw -> binding.viewPager.currentItem = 2
                }
                true
            }
        }
    }

    private fun ActivityMainBinding.viewPager() {/* Set up pages for preview */
        viewPager.offscreenPageLimit = 2
        ViewPagerAdapter(this@MainActivity, userId).apply {
            list = ArrayList<String>().apply {
                add(getString(R.string.home))
                add(getString(R.string.investments))
                add(getString(R.string.withdraw))
            }
        }.also { it.also { binding.viewPager.adapter = it } }

        /* Switch item selected in the bottom navigation bar using
        the view pager current page*/
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> menu.selectedItemId = R.id.home
                    1 -> menu.selectedItemId = R.id.investments
                    2 -> menu.selectedItemId = R.id.withdraw
                }
            }
        })
    }
}