package com.example.datacubedrecorder.data.database

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

        private var databaseInstance: MainDatabase? = null

        fun getDatabaseInstance(context: Context): MainDatabase? {
            if (databaseInstance == null) {
                databaseInstance =
                    Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return databaseInstance
        }
    }


}