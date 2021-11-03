package com.example.academyapp.domain.api.responses

import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("data") var trackResponses: List<TrackResponse>
)