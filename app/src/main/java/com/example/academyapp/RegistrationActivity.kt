package com.example.academyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.academyapp.databinding.ActivityRegistrationBinding
import com.example.academyapp.local.DataBase
import com.example.academyapp.local.UserDao

class RegistrationActivity : AppCompatActivity() {

    lateinit var userDao: UserDao
    private lateinit var binding: ActivityRegistrationBinding
    var fragmentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDao()
        navigation()
    }

    private fun initDao() {
        val dataBase = DataBase.getDataBase(this)
        userDao = dataBase.userDao()
    }

    private fun navigation() {
        with(binding) {
            registrationSwitch.setOnClickListener {
                fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
                if (fragmentId != R.id.registrationFragment) {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
                    loginSwitch.setTextColor(resources.getColor(R.color.nice_blue))
                    registrationSwitch.setTextColor(resources.getColor(R.color.white))
                }
            }

            loginSwitch.setOnClickListener {
                fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
                if (fragmentId != R.id.loginFragment) {
                    findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
                    registrationSwitch.setTextColor(resources.getColor(R.color.nice_blue))
                    loginSwitch.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}