package com.example.datacubedrecorder.data

import androidx.lifecycle.LiveData
import com.example.datacubedrecorder.data.database.dao.RecordingDao
import com.example.datacubedrecorder.data.database.model.RecordingModel

/**
 * Implementation class of [MainRepository] that uses [RecordingDao] to retrieve and save recordings
 */
class MainRepositoryImpl(private val recordingDao: RecordingDao): MainRepository {

    override fun getAllRecordings(): LiveData<List<RecordingModel>> {
        return recordingDao.getAllRecordings()
    }

    override fun insertRecording(recording: RecordingModel): Long {
        return recordingDao.insert(recording)
    }

    override fun clearDatabase() {
        recordingDao.deleteAll()
    }
}