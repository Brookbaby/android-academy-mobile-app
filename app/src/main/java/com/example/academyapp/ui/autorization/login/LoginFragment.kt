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
import com.example.academyapp.databinding.FragmentLoginBinding
import com.example.academyapp.domain.local.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val vm: LoginVM by viewModels()
    private lateinit var binding: FragmentLoginBinding
    var login = ""
    var password = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.loginButton.setOnClickListener {
            login = binding.loginEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            lifecycleScope.launchWhenResumed {
                if (checkLogin() && checkPassword()) {
                    vm.userRepository.putSession(true)
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
            }
        }
        return binding.root
    }

    private suspend fun checkLogin(): Boolean {
        with(binding) {
            return when {
                login.isBlank() -> {
                    loginInputLayout.error = "Поле не может быть пустым"
                    false
                }
                login.length < 4 -> {
                    loginInputLayout.error = "Слишком короткое имя пользователя"
                    false
                }
                isUserNotExist() -> {
                    loginInputLayout.error = "Пользователь не существует"
                    false
                }
                else -> {
                    loginInputLayout.error = null
                    true
                }
            }
        }
    }

    private suspend fun isUserNotExist(): Boolean {
        val user: Deferred<User?> = lifecycleScope.async {
            vm.userRepository.getUser(login)
        }
        return user.await() == null
    }

    private suspend fun checkPassword(): Boolean {
        with(binding) {
            return when {
                password.isBlank() -> {
                    passwordInputLayout.error = "Поле не может быть пустым"
                    false
                }
                password.length < 4 -> {
                    passwordInputLayout.error = "Недостаточное количество символов"
                    false
                }
                isPasswordNotExist() -> {
                    passwordInputLayout.error = "Неверный пароль"
                    false
                }
                else -> {
                    passwordInputLayout.error = null
                    true
                }
            }
        }
    }

    private suspend fun isPasswordNotExist(): Boolean {
        val user: Deferred<User?> = lifecycleScope.async {
            vm.userRepository.getUser(login)
        }
        return user.await()?.password != password
    }

}
