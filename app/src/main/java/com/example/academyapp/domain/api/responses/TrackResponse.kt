package com.example.academyapp.domain.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackResponse(
    @SerializedName("album") var albumResponse: AlbumResponse,
    @SerializedName("artist") var artistResponse: ArtistResponse,
    @SerializedName("duration") var duration: Int,
    @SerializedName("explicit_content_cover") var explicitContentCover: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("md5_image") var md5Image: String,
    @SerializedName("preview") var preview: String, /*mp3 файл*/
    @SerializedName("title") var title: String
) : Parcelable