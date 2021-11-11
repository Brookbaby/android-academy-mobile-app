package com.example.academyapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(val repository:Repository):ViewModel(){

    suspend fun getTracks() = repository.getTracksFromInternet()
}


