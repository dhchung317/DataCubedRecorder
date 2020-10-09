package com.example.datacubedrecorder.data

import androidx.lifecycle.LiveData
import com.example.datacubedrecorder.data.database.model.RecordingModel

interface MainRepository {

    fun getAllRecordings(): LiveData<List<RecordingModel>>

    fun insertRecording(recording: RecordingModel): Long

    fun clearDatabase()
}