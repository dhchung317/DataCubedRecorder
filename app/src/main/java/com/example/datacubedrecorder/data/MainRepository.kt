package com.example.datacubedrecorder.data

import com.example.datacubedrecorder.data.database.model.RecordingModel

interface MainRepository {

    fun getAllRecordings(): List<RecordingModel>

    fun insertRecording(recording: RecordingModel): Long

    fun clearDatabase()
}