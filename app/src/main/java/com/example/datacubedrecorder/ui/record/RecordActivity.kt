package com.example.datacubedrecorder.ui.record

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import kotlin.math.floor

class RecordActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var viewModel: MainViewModel

    private lateinit var timerTextView: TextView
    private lateinit var textureView: TextureView

    var counter = 0

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    private val tag = RecordActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        timerTextView = findViewById(R.id.timer_text)
        textureView = findViewById(R.id.textureView)

        // Request camera permissions
        if (allPermissionsGranted()) {
            textureView.post {
                startCamera()
                startCounter()
            }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun formatDuration(duration: Int): String {
        val minutes = floor(duration.toDouble() / 60).toInt()
        var seconds = duration - minutes * 60
        return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
    }

    private fun startCounter() {
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

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                textureView.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun startCamera() {
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().build()
// Build the viewfinder use case
        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = textureView.parent as ViewGroup
            parent.removeView(textureView)
            textureView.surfaceTexture = it.surfaceTexture
            parent.addView(textureView, 0)
        }

// Bind use cases to lifecycle
        CameraX.bindToLifecycle(this, preview)
    }
}

//TODO There should be a timer in this activity that displays how much time is left in the recording.
// Once the recording is complete, the activity should close by itself and return to the previous home screen with 2 tabs.