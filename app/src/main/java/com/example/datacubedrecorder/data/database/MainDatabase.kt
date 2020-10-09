package com.example.datacubedrecorder.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.datacubedrecorder.data.database.dao.RecordingDao
import com.example.datacubedrecorder.data.database.model.RecordingModel


@Database(version = 1, entities = [RecordingModel::class], exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun recordingDao(): RecordingDao

    companion object {
        private const val DATABASE_NAME = "data.db"

        @Volatile
        private var databaseInstance: MainDatabase? = null

        fun getDatabaseInstance(context: Context): MainDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(context).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(context: Context) =
            Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}