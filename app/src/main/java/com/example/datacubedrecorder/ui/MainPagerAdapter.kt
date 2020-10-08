package com.example.datacubedrecorder.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.datacubedrecorder.ui.fragment.camera.CameraFragment
import com.example.datacubedrecorder.ui.fragment.library.LibraryFragment

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CameraFragment()
            1 -> return LibraryFragment()
        }
        return CameraFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}