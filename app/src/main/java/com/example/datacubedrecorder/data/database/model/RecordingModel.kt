package com.example.datacubedrecorder.data.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * data class to hold information entered on [RecordEnterInfoFragment]
 *
 * @param title is the key, for now and //TODO will be checked against new entries
 *
 * @param duration holds the float data from the slider, representing seconds between 0 and 180,
 * and will need to be formatted to display minutes and seconds
 *
 * @param date holds the date the recording was started on, currently is in 'dd-MMMM-YYYY HH:mm' format
 *
 * @param image //TODO holds data to show an image capture of recording
 *
 * //TODO implement entry that will store a video recording, or a way to play recording from file system
 */

@Parcelize
@Entity(tableName = "recordings")
data class RecordingModel(
    @PrimaryKey
    val title: String,
    @ColumnInfo(name = "duration")
    val duration: Float,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "image")
    val image: String?
) : Parcelable