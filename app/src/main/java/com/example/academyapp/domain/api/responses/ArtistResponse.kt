package com.example.academyapp.domain.api.responses

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("picture") var picture: String,
    @SerializedName("picture_medium") var pictureMedium: String
)