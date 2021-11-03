package com.example.academyapp.ui.autorization.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationVM @Inject constructor(val repository: Repository) : ViewModel() {
    var login = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val rePassword = MutableLiveData("")
}
