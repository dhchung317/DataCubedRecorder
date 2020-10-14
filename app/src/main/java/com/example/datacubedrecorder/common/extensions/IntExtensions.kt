package com.example.datacubedrecorder.common.extensions

import kotlin.math.floor

fun Int.formatDuration(): String {
    val minutes = floor(this.toDouble() / 60).toInt()
    val seconds = this - minutes * 60
    return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
}