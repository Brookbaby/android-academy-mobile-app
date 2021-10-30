package com.example.academyapp.ui.track

import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackInfoVM @Inject constructor(val userRepository: UserRepository) : ViewModel()
