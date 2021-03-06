package com.example.datacubedrecorder.data

import androidx.lifecycle.LiveData
import com.example.datacubedrecorder.data.database.model.RecordingModel

/**
 * interface that has outlined methods to bridge instances of [MainDatabase] and [MainViewModel]
 */
interface MainRepository {

    fun getAllRecordings(): LiveData<List<RecordingModel>>

    fun hasItem(title: String): Boolean

    fun insertRecording(recording: RecordingModel): Long

    fun deleteRecordingByTitle(title: String)

    fun clearDatabase()
}