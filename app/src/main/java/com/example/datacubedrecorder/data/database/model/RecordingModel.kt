package com.example.datacubedrecorder.data.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * data class to hold information entered on [RecordEnterInfoFragment]
 *
 * @param title is the key
 *
 * @param duration holds the float data from the slider, representing seconds between 0 and 180,
 * and will need to be formatted to display minutes and seconds
 *
 * @param date holds the date the recording was started on, currently is in 'dd-MMMM-YYYY HH:mm' format
 *
 *@param path holds path of video file
 */

@Parcelize
@Entity(tableName = "recordings")
data class RecordingModel(
    @PrimaryKey
    val title: String,
    @ColumnInfo(name = "duration")
    var duration: Float,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "path")
    var path: String?
) : Parcelable