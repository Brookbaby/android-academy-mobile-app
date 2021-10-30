package com.example.academyapp.ui.track

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.academyapp.R
import com.example.academyapp.ui.autorization.registration.RegistrationVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackInfoFragment:Fragment(R.layout.fragment_trackinfo) {

    val vm: TrackInfoVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}