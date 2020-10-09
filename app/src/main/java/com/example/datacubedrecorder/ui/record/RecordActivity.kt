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

/**
 * This activity holds a TextureView and uses CameraX to preview camera, and record/save video
 *
 * Permissions are handled as soon as the camera wants access in this activity
 *
 * implements LifecycleOwner to bind CameraX
 */

//TODO look into factoring out permissions into manager class
//TODO create constant intent key variable
class RecordActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var viewModel: MainViewModel

    private lateinit var timerTextView: TextView
    private lateinit var textureView: TextureView

    var counter = 0
    private lateinit var recordingInfo: RecordingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        timerTextView = findViewById(R.id.timer_text)
        textureView = findViewById(R.id.textureView)

        // Request camera permissions
        if (allPermissionsGranted()) {
            recordingInfo = intent.getParcelableExtra("recording_data")
            textureView.post {
                startCamera()
                startCounter()
            }
        } else {
            ActivityCompat.requestPermissions(
                this, Companion.REQUIRED_PERMISSIONS, Companion.REQUEST_CODE_PERMISSIONS
            )
            finish()
        }
    }

    //TODO utils
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
                //TODO better handling of denied permissions
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
        for (permission in Companion.REQUIRED_PERMISSIONS) {
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

    /**
     * if the recording is cancelled, or the user presses back, the recording will be saved as is
     */
    override fun onDestroy() {
        if (this::recordingInfo.isInitialized) {
            if (counter != 0) {
                recordingInfo.duration = recordingInfo.duration - counter
            }
            viewModel.insertRecording(recordingInfo)
        }
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        private val TAG = RecordActivity::class.java
    }
}
//TODO screen transition when starting this activity needs to be smoother - no white screen/ global splash/loading screen