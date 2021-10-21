package com.example.academyapp.ui.autorization.login

import androidx.databinding.ObservableField
import androidx.databinding.ObservableParcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.properties.ObservableProperty

@HiltViewModel
class LoginVM @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    var login = ObservableField<String>()
    var password = MutableLiveData("")
    var repeatpassword = MutableLiveData("")
}

/*private fun checkUserIsLogined() {
    val isLogined = vm.sharedPreferences.getBoolean("session", false)
    if (isLogined == true) {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}*/

/**/



/*
}*/
