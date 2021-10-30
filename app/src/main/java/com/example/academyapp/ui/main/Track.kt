package com.example.academyapp.ui.main

import android.graphics.drawable.Drawable

data class Track(
    var trackName: String,
    var albumPhoto: Drawable? = null,
    var singerName: String,
    var trackDownloaded: Boolean = false
) {
}