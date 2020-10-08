package com.example.datacubedrecorder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.datacubedrecorder.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {
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

//TODO camera permissions check in main activity upon opening app

//TODO extensibility
//TODO prevent memory leaks
//TODO rotation/screen sizes