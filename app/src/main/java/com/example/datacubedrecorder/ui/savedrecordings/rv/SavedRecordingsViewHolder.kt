package com.example.datacubedrecorder.ui.savedrecordings.rv

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.CancellationSignal
import android.util.Size
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.common.extensions.formatDuration
import com.example.datacubedrecorder.data.database.model.RecordingModel
import java.io.File

class SavedRecordingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemTitleTextView: TextView = itemView.findViewById(R.id.item_title)
    private val itemDurationTextView: TextView = itemView.findViewById(R.id.item_duration)
    private val itemDateTextView: TextView = itemView.findViewById(R.id.item_date)
    private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
    private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_item_button)
    private val playButton: ImageButton = itemView.findViewById(R.id.saved_recordings_play_button)

    @RequiresApi(Build.VERSION_CODES.Q)
    fun onBind(
        recordingModel: RecordingModel,
        deleteListener: (RecordingModel) -> Unit,
        playListener: (RecordingModel) -> Unit
    ) {
        itemTitleTextView.text = recordingModel.title
        itemDurationTextView.text = recordingModel.duration.formatDuration()
        itemDateTextView.text = recordingModel.date
        itemImageView.setImageBitmap(getThumbnail(File(recordingModel.path)))
        playButton.setOnClickListener { playListener(recordingModel) }
        deleteButton.setOnClickListener { deleteListener(recordingModel) }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getThumbnail(file: File): Bitmap {
        return ThumbnailUtils.createVideoThumbnail(file, Size(200, 150), CancellationSignal())
    }
}