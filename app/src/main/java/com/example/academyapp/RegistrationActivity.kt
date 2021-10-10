package com.example.academyapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.academyapp.databinding.ActivityRegistrationBinding
import com.example.academyapp.local.DataBase
import com.example.academyapp.local.UserDao
import android.content.SharedPreferences


class RegistrationActivity : AppCompatActivity() {

    lateinit var userDao: UserDao
    lateinit var binding: ActivityRegistrationBinding
    var fragmentId = 0
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDao()
        initPrefs()
        checkUserIsLogined()
        with(binding) {
            registrationSwitch.setOnClickListener {
                navigateToRegistration()
            }

            loginSwitch.setOnClickListener {
                navigateToLogin()
            }
        }
    }

    private fun initDao() {
        val dataBase = DataBase.getDataBase(this)
        userDao = dataBase.userDao()
    }

    fun navigateToLogin() {
        fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
        if (fragmentId != R.id.loginFragment) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_registrationFragment_to_loginFragment)
            binding.registrationSwitch.setTextColor(resources.getColor(R.color.nice_blue))
            binding.loginSwitch.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun navigateToRegistration() {
        fragmentId = findNavController(R.id.fragmentContainerView).currentDestination?.id ?: 0
        if (fragmentId != R.id.registrationFragment) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registrationFragment)
            binding.loginSwitch.setTextColor(resources.getColor(R.color.nice_blue))
            binding.registrationSwitch.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun initPrefs() {
        prefs = getSharedPreferences("academyApp", Context.MODE_PRIVATE)
    }

    private fun checkUserIsLogined() {
        val isLogined = prefs?.getBoolean("session", false)
        if (isLogined == true) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}