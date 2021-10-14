package com.example.academyapp.domain

import android.content.SharedPreferences
import com.example.academyapp.domain.local.UserDao
import com.example.academyapp.domain.local.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(val userDao: UserDao, val sharedPrefs: SharedPreferences) {

    //TODO дописать функцию setSession(session:Boolean)

    suspend fun getSession() = sharedPrefs.getBoolean("session", false)

    suspend fun getUser(login: String, password: String) = userDao.getUser(login, password)

    suspend fun addUser(login: String, password: String) {
        val user = User(login = login, password = password)
        userDao.addUser(user)
    }
}