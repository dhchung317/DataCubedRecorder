package com.example.datacubedrecorder.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.datacubedrecorder.ui.fragment.record.RecordEnterInfoFragment
import com.example.datacubedrecorder.ui.fragment.savedrecordings.SavedRecordngsFragment

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return RecordEnterInfoFragment()
            1 -> return SavedRecordngsFragment()
        }
        return RecordEnterInfoFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}