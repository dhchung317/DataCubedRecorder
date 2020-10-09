package com.example.datacubedrecorder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datacubedrecorder.data.database.model.RecordingModel

@Dao
interface RecordingDao {

    @Query("SELECT * FROM recordings")
    suspend fun getAllRecordings(): List<RecordingModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recording: RecordingModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg recordings: RecordingModel): List<Long>

    @Query("DELETE FROM recordings")
    fun deleteAll()
}
