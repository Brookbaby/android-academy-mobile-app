package com.example.academyapp.ui.autorization.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(val repository: Repository) : ViewModel() {

    var login = ObservableField<String>()
    var password = MutableLiveData("")
    var repeatpassword = MutableLiveData("")
}

