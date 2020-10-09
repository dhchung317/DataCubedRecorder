package com.example.datacubedrecorder.ui.record

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import kotlin.math.floor

class RecordActivity: AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var testText: TextView
    private lateinit var timerTextView:TextView

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        timerTextView = findViewById(R.id.timer_text)
//        if(intent.hasExtra("recording_data")){
//            val recordingInfo: RecordingModel = intent.getParcelableExtra("recording_data")
//            testText.text =
//                "title:${recordingInfo.title} duration:${recordingInfo.duration} date:${recordingInfo.date} image:${recordingInfo.image}"
//        }
        startCounter()
    }

    private fun formatDuration(duration: Int): String {
        val minutes = floor(duration.toDouble() / 60).toInt()
        var seconds = duration - minutes * 60

        return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
    }

    fun startCounter() {
        val recordingInfo: RecordingModel = intent.getParcelableExtra("recording_data")
        counter = recordingInfo.duration.toInt()
        object : CountDownTimer((counter * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = formatDuration(counter)
                counter--
            }
            override fun onFinish() {
                //TODO save recoding info to database
                finish()
            }
        }.start()
    }
}

//TODO There should be a timer in this activity that displays how much time is left in the recording.
// Once the recording is complete, the activity should close by itself and return to the previous home screen with 2 tabs.