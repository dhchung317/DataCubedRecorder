package com.example.datacubedrecorder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datacubedrecorder.data.database.dao.RecordingDao
import com.example.datacubedrecorder.data.database.model.RecordingModel

@Database(version = 1, entities = [RecordingModel::class], exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun recordingDao(): RecordingDao

    companion object {
        const val DATABASE_NAME = "data.db"
    }
}