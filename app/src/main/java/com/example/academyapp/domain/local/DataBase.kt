package com.example.academyapp.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.academyapp.domain.local.entity.TrackDto
import com.example.academyapp.domain.local.entity.UserDto

@Database(entities = [UserDto::class, TrackDto::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getTrackDao(): TrackDao
}