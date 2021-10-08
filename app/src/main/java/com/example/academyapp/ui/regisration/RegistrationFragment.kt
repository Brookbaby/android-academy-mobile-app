package com.example.academyapp.ui.regisration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.academyapp.RegistrationActivity
import com.example.academyapp.databinding.FragmentRegistrationBinding
import com.example.academyapp.local.entity.User

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val registrationActivity = requireActivity() as RegistrationActivity
        binding.registrationButton.setOnClickListener {
            val user = User(
                login = binding.loginEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
            validateLogin()
            validateEmail()
            validatePassword()
            lifecycleScope.launchWhenResumed {
                registrationActivity.userDao.addUser(user)
            }
        }
        return binding.root
    }

    fun validateLogin(){
        var login = binding.loginEditText.text.toString()
        lifecycleScope.launchWhenResumed {
            when{
                login.isBlank() -> binding.loginInputLayout.error = "Поле не может быть пустым"
                login.length<4 -> binding.loginInputLayout.error = "Слишком короткое имя пользователя"
                else -> binding.loginInputLayout.error = null
            }
        }
    }
    fun validateEmail(){
        var email = binding.emailEditText.text.toString()
        lifecycleScope.launchWhenResumed {
            when{
                email.isBlank() -> binding.emailInputLayout.error = "Поле не может быть пустым"
                email.length<6 -> binding.emailInputLayout.error = "Недостаточное количество симовлов"
                else -> binding.emailInputLayout.error = null
            }
        }
    }
    fun validatePassword(){
        var password = binding.passwordEditText.text.toString()
        lifecycleScope.launchWhenResumed {
            when{
                password.isBlank() -> binding.passwordInputLayout.error = "Поле не может быть пустым"
                password.length<4 -> binding.passwordInputLayout.error = "Недостаточное количество символов"
                else -> binding.passwordInputLayout.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}