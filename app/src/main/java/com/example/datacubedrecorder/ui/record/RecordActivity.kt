package com.example.datacubedrecorder.ui.record

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Size
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.common.extensions.formatDuration
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import com.example.datacubedrecorder.ui.record.enterinfo.RecordEnterInfoFragment.Companion.RECORDING_DATA_KEY
import java.io.File


/**
 * This activity holds a TextureView and uses CameraX to preview camera, and record/save video
 *
 * Permissions are handled as soon as the camera wants access in this activity
 *
 * implements LifecycleOwner to bind CameraX
 */

//TODO look into factoring out permissions into manager class
class RecordActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var viewModel: MainViewModel

    private lateinit var timerTextView: TextView
    private lateinit var textureView: TextureView
    private lateinit var videoCapture: VideoCapture

    private lateinit var timer: CountDownTimer
    private lateinit var recordingInfo: RecordingModel

    private var counter: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        timerTextView = findViewById(R.id.timer_text)
        textureView = findViewById(R.id.textureView)

        if (allPermissionsGranted()) {
            startWithPermissions()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun startCounter() {
        val recordingInfo: RecordingModel = intent.getParcelableExtra(RECORDING_DATA_KEY)
        counter = recordingInfo.duration
        timer = object : CountDownTimer((counter * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter--
                timerTextView.text = counter.formatDuration()
            }

            override fun onFinish() {
                stopRecording()
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

    private fun startWithPermissions() {
        recordingInfo = intent.getParcelableExtra(RECORDING_DATA_KEY)
        textureView.post {
            startCamera()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
            startCounter()
            startRecording()
        }

    }

    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        var size = Size(1280, 720)

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(size)
            setTargetRotation(textureView.display.rotation)
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {

            val parent = textureView.parent as ViewGroup
            parent.removeView(textureView)
            textureView.surfaceTexture = it.surfaceTexture
            parent.addView(textureView, 0)
        }

        val videoCaptureConfig = VideoCaptureConfig.Builder().apply {
            setTargetResolution(size)
            setTargetRotation(textureView.display.rotation)
        }.build()

        videoCapture = VideoCapture(videoCaptureConfig)

        textureView.requestLayout()
        textureView.invalidate()

        CameraX.bindToLifecycle(this, preview, videoCapture)
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        val file = File(
            externalMediaDirs.first(),
            "${recordingInfo.title}.mp4"
        )
        videoCapture.startRecording(file, object : VideoCapture.OnVideoSavedListener {
            override fun onVideoSaved(file: File?) {
                if (file != null) {
                    saveRecordingInfo(file.path)
                }
            }

            override fun onError(
                useCaseError: VideoCapture.UseCaseError?,
                message: String?,
                cause: Throwable?
            ) {
                Log.d("error", "Video Error: $message")
            }
        })
    }

    /**
     * if the recording is cancelled, or the user presses back, the recording will be saved as is
     */
    override fun onBackPressed() {
        stopRecording()
        super.onBackPressed()
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        videoCapture.stopRecording()
        timer.cancel()
    }

    private fun saveRecordingInfo(path: String) {
        if (this::recordingInfo.isInitialized) {
            if (counter != 0f) {
                recordingInfo.duration = recordingInfo.duration - counter
            }
            recordingInfo.path = path
            viewModel.insertRecording(recordingInfo)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    }
}
//TODO screen transition when starting this activity needs to be smoother - no white screen/ global splash/loading screen
