package com.example.datacubedrecorder.ui.play

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.datacubedrecorder.R

class PlayActivity : AppCompatActivity() {
    private lateinit var playButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        playButton = findViewById(R.id.play)

        val videoView: VideoView = findViewById(R.id.videoView)
        val uri: Uri = Uri.parse(intent.getStringExtra("video_path"))
        videoView.setVideoURI(uri)
        videoView.start()
        playButton.setOnClickListener {
            if(!videoView.isPlaying) {
                playButton.visibility = View.INVISIBLE
                videoView.start()
            }
        }
        videoView.setOnCompletionListener {
            playButton.visibility = View.VISIBLE
        }

//        videoView.pause()
//        videoView.resume()
    }
}