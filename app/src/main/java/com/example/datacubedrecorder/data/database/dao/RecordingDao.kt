package com.example.datacubedrecorder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datacubedrecorder.data.database.model.RecordingModel

@Dao
interface RecordingDao {

    @Query("SELECT * FROM recordings")
    fun getAllRecordings(): List<RecordingModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: RecordingModel): Long

    @Query("DELETE FROM recordings")
    fun deleteAll()
}
