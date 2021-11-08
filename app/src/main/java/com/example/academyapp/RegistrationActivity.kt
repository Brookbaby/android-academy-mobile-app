package com.example.academyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.academyapp.databinding.ActivityRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    var fragmentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            registrationSwitch.setOnClickListener {
                navigateToRegistration()
            }

            loginSwitch.setOnClickListener {
                navigateToLogin()
            }
        }
    }

    fun navigateToLogin() {
        fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
        if (fragmentId != R.id.loginFragment) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
            binding.registrationSwitch.setTextColor(resources.getColor(R.color.main_color))
            binding.loginSwitch.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun navigateToRegistration() {
        fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
        if (fragmentId != R.id.registrationFragment) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
            binding.loginSwitch.setTextColor(resources.getColor(R.color.main_color))
            binding.registrationSwitch.setTextColor(resources.getColor(R.color.white))
        }
    }
}