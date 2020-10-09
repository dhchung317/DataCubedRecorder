package com.example.datacubedrecorder.ui.savedrecordings.rv

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import kotlinx.android.synthetic.main.recording_itemview.view.*
import kotlin.math.floor

class SavedRecordingsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    private val itemTitleTextView: TextView = itemView.findViewById(R.id.item_title)
    private val itemDurationTextView: TextView = itemView.findViewById(R.id.item_duration)
    private val itemDateTextView: TextView = itemView.findViewById(R.id.item_date)
    private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
    private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_item_button)

    fun onBind(recordingModel: RecordingModel, deleteListener:(RecordingModel) -> Unit) {
        itemTitleTextView.text = recordingModel.title
        itemDurationTextView.text = formatDuration(recordingModel.duration)
        itemDateTextView.text = recordingModel.date
        itemImageView.setImageResource(R.drawable.recording_image_placeholder)
        deleteButton.setOnClickListener{deleteListener(recordingModel)}
    }
//TODO utils
    private fun formatDuration(duration: Float): String {
        val minutes = floor(duration / 60).toInt()
        var seconds = duration.toInt() - minutes * 60
        return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
    }
}