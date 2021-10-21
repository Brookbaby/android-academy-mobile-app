package com.example.academyapp.domain

import android.content.SharedPreferences
import com.example.academyapp.domain.local.UserDao
import com.example.academyapp.domain.local.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao, private val sharedPrefs: SharedPreferences) {

    suspend fun putSession(session: Boolean) {
        sharedPrefs.edit().putBoolean("session", session).apply()
    }

    suspend fun getSession() = sharedPrefs.getBoolean("session", false)

    suspend fun getUser(login: String, password: String) = userDao.getUser(login, password)

    suspend fun addUser(login: String, password: String, email: String) {
        val user = User(login = login, password = password, email = email)
        userDao.addUser(user)
    }
}