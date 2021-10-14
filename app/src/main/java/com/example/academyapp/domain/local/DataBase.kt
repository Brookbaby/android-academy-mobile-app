package com.example.academyapp.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.academyapp.domain.local.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}