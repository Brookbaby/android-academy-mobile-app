package com.example.academyapp.domain.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tracks_table")
data class TrackDto(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var trackName: String,
    var singerName: String,
    var cover: String,
    var downloadLink: String
) : Parcelable
