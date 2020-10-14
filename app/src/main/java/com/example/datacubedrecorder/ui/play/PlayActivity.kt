package com.example.datacubedrecorder.ui.play

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.datacubedrecorder.R


class PlayActivity : AppCompatActivity() {
    private lateinit var playButton: ImageButton
    private lateinit var videoView: VideoView
    private var start: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            start = savedInstanceState.getInt(FRAME_KEY)
        }
        setContentView(R.layout.activity_play)

        playButton = findViewById(R.id.play)
        videoView = findViewById(R.id.videoView)

        val uri: Uri = Uri.parse(intent.getStringExtra("video_path"))
        videoView.setVideoURI(uri)

        playButton.setOnClickListener {
            if (!videoView.isPlaying) {
                playButton.visibility = View.INVISIBLE
                videoView.start()
            }
        }

        videoView.setOnCompletionListener {
            playButton.visibility = View.VISIBLE
        }

        videoView.setOnPreparedListener { mp ->
            mp.setOnSeekCompleteListener { mp -> mp.start() }
        }

        if (start > 0) {
            videoView.seekTo(start)
        }else{
            videoView.start()
        }
//        videoView.pause()
//        videoView.resume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FRAME_KEY, start)
        Log.d("current", start.toString())
    }

    override fun onPause() {
        super.onPause()
        start = videoView.currentPosition
    }

    companion object {
        const val FRAME_KEY = "frame_key"
    }
}