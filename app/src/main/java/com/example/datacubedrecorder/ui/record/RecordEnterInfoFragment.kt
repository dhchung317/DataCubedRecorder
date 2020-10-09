package com.example.datacubedrecorder.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.ui.MainViewModel
import com.google.android.material.slider.Slider

class RecordEnterInfoFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private lateinit var title: EditText
    private lateinit var slider: Slider
    private lateinit var time: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO check permissions when clicking record

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_record_enter_info, container, false);
    }
//    continuousSlider.setLabelFormatter { value: Float ->
//        return@setLabelFormatter "$${value.roundToInt()}"
//    }


//    slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
//        override fun onStartTrackingTouch(slider: Slider) {
//            // Responds to when slider's touch event is being started
//        }
//
//        override fun onStopTrackingTouch(slider: Slider) {
//            // Responds to when slider's touch event is being stopped
//        }
//    })
//
//    slider.addOnChangeListener { slider, value, fromUser ->
//        // Responds to when slider's value is changed
//    }
}
//TODO In this tab user should be able to enter the name of the recording in a text field
// and enter the duration of recording using a slider with a minimum of 15 seconds and a maximum of 3 minutes.
// On pressing the record button a new activity should open which starts recording the video of the user for that set duration.