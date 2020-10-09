package com.example.datacubedrecorder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.datacubedrecorder.data.MainRepository
import com.example.datacubedrecorder.data.MainRepositoryImpl
import com.example.datacubedrecorder.data.database.MainDatabase
import com.example.datacubedrecorder.data.database.model.RecordingModel

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository
    val recordings: LiveData<List<RecordingModel>>

    init{
        val recordingDao = MainDatabase.getDatabaseInstance(application).recordingDao()
        repository = MainRepositoryImpl(recordingDao)
        recordings = repository.getAllRecordings()
    }

    fun insertRecording(recordingModel: RecordingModel){
        repository.insertRecording(recordingModel)
    }
}