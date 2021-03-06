package com.example.academyapp.domain.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class UserDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var login: String,
    var password: String,
    var email:String
) : Parcelable
