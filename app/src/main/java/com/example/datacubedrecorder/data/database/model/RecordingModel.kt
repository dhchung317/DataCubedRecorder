package com.example.datacubedrecorder.data.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recordings")
data class RecordingModel(
    @PrimaryKey
    val title: String,
    @ColumnInfo(name = "duration")
    val duration: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "image")
    val image: String?
) : Parcelable