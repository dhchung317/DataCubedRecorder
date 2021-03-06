package com.example.datacubedrecorder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.datacubedrecorder.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * MainActivity that contains a tabLayout/viewPager which navigates to two fragments.
 *
 * [RecordEnterInfoFragment] shows a form to enter recording info and start recording,
 * [SavedRecordingsFragment] displays saved recordings in a recyclerView
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewpager)
        viewPager.isUserInputEnabled = false
        val viewPagerAdapter = MainPagerAdapter(supportFragmentManager, this.lifecycle)
        viewPager.adapter = viewPagerAdapter

        tabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            if (position == 0) {
                tab.setText(R.string.camera_tab_text)
            } else if (position == 1) {
                tab.setText(R.string.library_tab_text)
            }
        }.attach()
    }
}
//TODO extensibility
//TODO screen size adaptability / min/max height / scaling fonts
