package com.example.datacubedrecorder.ui.savedrecordings.rv

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import kotlinx.android.synthetic.main.recording_itemview.view.*

class SavedRecordingsAdapter(private val deleteListener:(RecordingModel) -> Unit, private val playListener:(RecordingModel) -> Unit) :
    RecyclerView.Adapter<SavedRecordingsViewHolder>() {
    private var recordingModels: List<RecordingModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecordingsViewHolder {
        return SavedRecordingsViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recording_itemview, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: SavedRecordingsViewHolder, position: Int) {
        holder.onBind(recordingModels[position], deleteListener, playListener)
    }

    override fun getItemCount(): Int {
        return recordingModels.size
    }
//TODO look into better update logic
    fun update(recordings: List<RecordingModel>) {
        recordingModels = recordings
        notifyDataSetChanged()
    }
}