package com.example.academyapp.ui.regisration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.academyapp.MainActivity
import com.example.academyapp.RegistrationActivity
import com.example.academyapp.databinding.FragmentLoginBinding
import com.example.academyapp.local.entity.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class LoginFragment : Fragment() {

    lateinit var user: User
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            validateLogin()
            validatePassword()
            }
        return binding.root
    }

    fun validateLogin() {
        val login = binding.loginEditText.text.toString()
        lifecycleScope.launchWhenResumed {
            when {
                login.isBlank() -> binding.loginInputLayout.error = "Поле не может быть пустым"
                login.length<4 -> binding.loginInputLayout.error = "Слишком короткое имя пользователя"
                isUserNotExist(login) -> binding.loginInputLayout.error = "Пользователь не существует"
                else -> binding.loginInputLayout.error = null
            }
        }
    }

    suspend fun isUserNotExist(login:String):Boolean{
        val registrationActivity = requireActivity() as RegistrationActivity
        val user:Deferred<User>? = lifecycleScope.async {
             registrationActivity.userDao.getUser(login)
        }
       return user?.await()?.login == null
    }
    fun validatePassword(){
        val password = binding.passwordEditText.text.toString()
        val login = binding.loginEditText.text.toString()
        lifecycleScope.launchWhenResumed {
            when{
                password.isBlank() -> binding.passwordInputLayout.error = "Поле не может быть пустым"
                isPasswordNotExist(password,login) -> binding.passwordInputLayout.error = "Неверный пароль"
                else -> binding.passwordInputLayout.error = null
            }
        }
       }

   suspend fun isPasswordNotExist(password:String,login: String):Boolean{
       val registrationActivity = requireActivity() as RegistrationActivity
           return registrationActivity.userDao.getUser(login)!= null&& registrationActivity.userDao.getUser(login).password != password
   }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}