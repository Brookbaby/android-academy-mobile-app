package com.example.academyapp.domain.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumResponse(
    @SerializedName("cover") var cover: String,
    @SerializedName("cover_xl") var coverXl: String,
    @SerializedName("id") var id: Int
) : Parcelable