package com.example.datacubedrecorder.ui.record.enterinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.common.extensions.formatDuration
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import com.example.datacubedrecorder.ui.record.RecordActivity
import com.google.android.material.slider.Slider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * This fragment holds the form to output the data needed to start and save a recording
 *
 * The 'start recording' button will send a bundle of data to be deciphered in the triggered [RecordActivity]
 */
class RecordEnterInfoFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private lateinit var titleEditText: EditText
    private lateinit var slider: Slider
    private lateinit var displayTime: TextView
    private lateinit var recordButton: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record_enter_info, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        titleEditText = view.findViewById(R.id.enter_title_editText)
        slider = view.findViewById(R.id.slider)
        setupSlider()
        displayTime = view.findViewById(R.id.display_time_textView)
        recordButton = view.findViewById(R.id.record_button)
        recordButton.setOnClickListener {
            if (viewModel.checkTitle(titleEditText.text.toString())) {
                Toast.makeText(
                    requireContext(),
                    "Title exists! Enter a different title",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (slider.value < 1) {
                Toast.makeText(
                    requireContext(),
                    "Duration must be longer than 0 seconds!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                startRecording()
            }
        }
    }

    private fun startRecording() {
        val intent = Intent(activity, RecordActivity::class.java)
        intent.putExtra(RECORDING_DATA_KEY, getRecordingInfo())
        titleEditText.text.clear()
        startActivity(intent)
    }

    private fun getRecordingInfo(): RecordingModel {
        val title = titleEditText.text.toString().trim()
        val duration = slider.value
        return RecordingModel(
            title = title,
            duration = duration,
            date = getDate(),
            path = null
        )
    }

    private fun getDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")
        return LocalDateTime.now().format(formatter)
    }

    private fun setupSlider() {
        slider.setLabelFormatter { value ->
            return@setLabelFormatter value.formatDuration()
        }
        slider.addOnChangeListener { slider, value, fromUser ->
            displayTime.text = value.formatDuration()
        }
    }

    companion object {
        const val RECORDING_DATA_KEY = "recording_data_key"
    }
}