package com.example.datacubedrecorder.ui.fragment.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.ui.MainViewModel

class RecordActivity: AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}

//TODO There should be a timer in this activity that displays how much time is left in the recording.
// Once the recording is complete, the activity should close by itself and return to the previous home screen with 2 tabs.