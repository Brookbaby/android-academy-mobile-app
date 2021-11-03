package com.example.academyapp.domain.api.responses

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("cover") var cover: String,
    @SerializedName("cover_medium") var coverMedium: String,
    @SerializedName("id") var id: Int
)