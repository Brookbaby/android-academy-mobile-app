package com.example.academyapp.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.academyapp.domain.api.responses.TrackResponse
import com.example.academyapp.domain.local.entity.TrackDto

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTracks(track: TrackDto)

    @Query("SELECT * FROM tracks_table")
    suspend fun getTracks(): List<TrackDto>
}