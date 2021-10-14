package com.example.academyapp.ui.autorization.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.academyapp.MainActivity
import com.example.academyapp.RegistrationActivity
import com.example.academyapp.databinding.FragmentLoginBinding
import com.example.academyapp.domain.local.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val vm: LoginVM by viewModels()
    private lateinit var binding: FragmentLoginBinding
    private var login = ""
    private var password = ""
    private lateinit var registrationActivity: RegistrationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        checkUserIsLogined()
        registrationActivity = requireActivity() as RegistrationActivity
        binding.loginButton.setOnClickListener {
            login = binding.loginEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            lifecycleScope.launchWhenResumed {
                if (validateLogin() && validatePassword()) {
                    vm.sharedPreferences.edit()?.putBoolean("session", true)?.apply()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
            }
        }
        return binding.root
    }

    private fun checkUserIsLogined() {
        val isLogined = vm.sharedPreferences.getBoolean("session", false)
        if (isLogined == true) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private suspend fun validateLogin(): Boolean {
        return when {
            login.isBlank() -> {
                binding.loginInputLayout.error = "Поле не может быть пустым"
                false
            }
            login.length < 4 -> {
                binding.loginInputLayout.error = "Слишком короткое имя пользователя"
                false
            }
            isUserNotExist() -> {
                binding.loginInputLayout.error = "Пользователь не существует"
                false
            }
            else -> {
                binding.loginInputLayout.error = null
                true
            }
        }
    }

    private suspend fun isUserNotExist(): Boolean {
        val user: Deferred<User?> = lifecycleScope.async {
            registrationActivity.userDao.getUser(login)
        }
        return user.await() == null
    }

    private suspend fun validatePassword(): Boolean {
        return when {
            password.isBlank() -> {
                binding.passwordInputLayout.error = "Поле не может быть пустым"
                false
            }
            password.length < 4 -> {
                binding.passwordInputLayout.error = "Недостаточное количество символов"
                false
            }
            isPasswordNotExist() -> {
                binding.passwordInputLayout.error = "Неверный пароль"
                false
            }
            else -> {
                binding.passwordInputLayout.error = null
                true
            }
        }
    }

    private suspend fun isPasswordNotExist(): Boolean {
        val user: User = registrationActivity.userDao.getUser(login)
        return user == null && user?.password != password
    }
}
