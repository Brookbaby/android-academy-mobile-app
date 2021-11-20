package com.example.academyapp.domain

import android.content.SharedPreferences
import com.example.academyapp.domain.api.MusicService
import com.example.academyapp.domain.api.responses.TrackResponse
import com.example.academyapp.domain.api.responses.TracksResponse
import com.example.academyapp.domain.api.responses.toDomain
import com.example.academyapp.domain.local.TrackDao
import com.example.academyapp.domain.local.UserDao
import com.example.academyapp.domain.local.entity.TrackDto
import com.example.academyapp.domain.local.entity.UserDto
import javax.inject.Inject

class Repository @Inject constructor(
    private val userDao: UserDao,
    private val trackDao: TrackDao,
    private val sharedPrefs: SharedPreferences,
    private val musicService: MusicService
) {

    suspend fun setLogin(login: String) {
        sharedPrefs.edit().putString("login", login).apply()
    }

    suspend fun getLogin() = sharedPrefs.getString("login", "")

    suspend fun putSession(session: Boolean) {
        sharedPrefs.edit().putBoolean("session", session).apply()
    }

    suspend fun getSession() = sharedPrefs.getBoolean("session", false)

    suspend fun getUser(login: String) = userDao.getUser(login)

    suspend fun addUser(login: String, password: String, email: String) {
        val user = UserDto(login = login, password = password, email = email)
        userDao.addUser(user)
    }

    suspend fun addTrack(track: TrackResponse) {
        trackDao.addTracks(track.toDomain())
    }

    suspend fun getTracksFromInternet() = musicService.getRecentTracks()

    suspend fun getTracksFromDataBase() = trackDao.getTracks()

    suspend fun deleteTrack(trackName: String, singerName: String) {
        trackDao.deleteTrack(trackName ,singerName)
    }

}