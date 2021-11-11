package com.example.academyapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(val repository: Repository) : ViewModel(){

    suspend fun getTracks() = repository.getTracksFromDataBase()
}
