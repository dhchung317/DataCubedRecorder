package com.example.datacubedrecorder.ui.record

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import com.google.android.material.slider.Slider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor
import kotlin.math.roundToInt

class RecordEnterInfoFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private lateinit var titleEditText: EditText
    private lateinit var slider: Slider
    private lateinit var displayTime: TextView
    private lateinit var recordButton: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record_enter_info, container, false);
    }

    private fun initViews() {
        titleEditText = requireActivity().findViewById(R.id.enter_title_editText)
        slider = requireActivity().findViewById(R.id.slider)
        setupSlider()
        displayTime = requireActivity().findViewById(R.id.display_time_textView)
        recordButton = requireActivity().findViewById(R.id.record_button)
        recordButton.setOnClickListener { startRecording() }
    }

    private fun startRecording() {
        //TODO check permissions when clicking record
        val intent = Intent(activity, RecordActivity::class.java)
        intent.putExtra("recording_data", getRecordingInfo())
        startActivity(intent)
    }

    private fun getRecordingInfo(): RecordingModel {
        val title = titleEditText.text.toString()
        val duration = displayTime.text.toString()
        return RecordingModel(
            title = title,
            duration = duration,
            date = getDate(),
            null
        )
    }

    private fun getDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")
        return LocalDateTime.now().format(formatter)
    }

    private fun setupSlider() {
        slider.setLabelFormatter { value ->
            return@setLabelFormatter "${value.roundToInt()}"
        }

        slider.addOnChangeListener { slider, value, fromUser ->
            displayTime.text = formatDuration(value)
        }
    }

    private fun formatDuration(duration: Float): String {
        val minutes = floor(duration / 60).toInt()
        var seconds = duration.toInt() - minutes * 60

        return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
    }

}

//TODO In this tab user should be able to enter the name of the recording in a text field
// and enter the duration of recording using a slider with a minimum of 15 seconds and a maximum of 3 minutes.
// On pressing the record button a new activity should open which starts recording the video of the user for that set duration.