package com.example.academyapp.ui.autorization.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(val sharedPreferences: SharedPreferences) : ViewModel() {
}