package com.example.academyapp.domain.api

import com.example.academyapp.domain.api.responses.TracksResponse
import retrofit2.Call
import retrofit2.http.GET

interface MusicService {

    @GET("/user/${ApiConfig.MY_ID}/flow")
    suspend fun getRecentTracks(): TracksResponse
}