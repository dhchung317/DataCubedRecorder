package com.example.datacubedrecorder.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.datacubedrecorder.ui.record.RecordEnterInfoFragment
import com.example.datacubedrecorder.ui.savedrecordings.SavedRecordingsFragment

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return RecordEnterInfoFragment()
            1 -> return SavedRecordingsFragment()
        }
        return RecordEnterInfoFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}