package com.example.datacubedrecorder.ui.savedrecordings.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel

class SavedRecordingsAdapter(private val recordingModels: List<RecordingModel>): RecyclerView.Adapter<SavedRecordingsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecordingsViewHolder {
        return SavedRecordingsViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.recording_itemview, parent, false))
    }

    override fun onBindViewHolder(holder: SavedRecordingsViewHolder, position: Int) {
        holder.onBind(recordingModels[position])
    }

    override fun getItemCount(): Int {
        return recordingModels.size
    }
}