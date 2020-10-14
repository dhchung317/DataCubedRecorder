package com.example.datacubedrecorder.common.extensions

import kotlin.math.floor

fun Float.formatDuration(): String {
    val minutes = floor(this / 60).toInt()
    var seconds = this.toInt() - minutes * 60
    return if (seconds >= 10) "${minutes}:${seconds}" else "${minutes}:0${seconds}"
}