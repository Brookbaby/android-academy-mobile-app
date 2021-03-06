package com.example.academyapp.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.academyapp.domain.local.entity.UserDto

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userDto: UserDto)

    @Query("SELECT * FROM user_table u WHERE u.login= :login")
    suspend fun getUser(login: String):UserDto

}