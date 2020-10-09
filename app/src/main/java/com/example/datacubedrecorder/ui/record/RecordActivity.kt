package com.example.datacubedrecorder.ui.record

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel

class RecordActivity: AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var testText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        testText = findViewById(R.id.testText)
        if(intent.hasExtra("recording_data")){
            val recordingInfo: RecordingModel = intent.getParcelableExtra("recording_data")
            testText.text =
                "title:${recordingInfo.title} duration:${recordingInfo.duration} date:${recordingInfo.date} image:${recordingInfo.image}"
        }
    }


}

//TODO There should be a timer in this activity that displays how much time is left in the recording.
// Once the recording is complete, the activity should close by itself and return to the previous home screen with 2 tabs.