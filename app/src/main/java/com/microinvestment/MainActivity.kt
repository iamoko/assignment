package com.microinvestment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.microinvestment.adapters.ViewPagerAdapter
import com.microinvestment.data.db.AppDatabase
import com.microinvestment.data.models.User
import com.microinvestment.databinding.ActivityMainBinding
import com.microinvestment.utils.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        /** To avoid blocking the main thread */
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@MainActivity)
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserById(userId)
            }

            if (user != null) loadUserPortfolio(user)
        }

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
        ViewPagerAdapter(supportFragmentManager, userId).apply {
            list = ArrayList<String>().apply {
                add(getString(R.string.home))
                add(getString(R.string.investments))
                add(getString(R.string.withdraw))
            }
        }.also { it.also { binding.viewPager.adapter = it } }

        /* Switch item selected in the bottom navigation bar using
        the view pager current page*/
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> menu.selectedItemId = R.id.home
                    1 -> menu.selectedItemId = R.id.investments
                    2 -> menu.selectedItemId = R.id.withdraw
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun loadUserPortfolio(user: User) {


        // You could also add buttons or other UI elements to handle further actions such as investment, withdrawal, etc.
    }
}