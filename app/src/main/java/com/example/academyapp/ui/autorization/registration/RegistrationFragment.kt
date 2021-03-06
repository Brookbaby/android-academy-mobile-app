package com.example.academyapp.ui.autorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.academyapp.RegistrationActivity
import com.example.academyapp.databinding.FragmentRegistrationBinding
import com.example.academyapp.domain.local.entity.UserDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    val vm: RegistrationVM by viewModels()
    private lateinit var binding: FragmentRegistrationBinding
    private var login = ""
    private var email = ""
    private var password = ""
    private var repeatPassword = ""
    private lateinit var registrationActivity: RegistrationActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        registrationActivity = requireActivity() as RegistrationActivity
        binding.registrationButton.setOnClickListener {
            login = binding.loginEditText.text.toString()
            email = binding.emailEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            repeatPassword = binding.repeatPasswordEditText.text.toString()
            val rulsIsChecked = binding.rulsSwitch.isChecked
            lifecycleScope.launchWhenResumed {
                if (checkLogin() && checkMail() && checkPassword() && checkSecondPassword()) {
                    if (rulsIsChecked) {
                        vm.repository.addUser(login,password,email)
                        registrationActivity.navigateToLogin()
                        (registrationActivity.binding.fragmentsSwitch.getChildAt(0) as RadioButton).isChecked = true
                    } else {
                        RulesErrorDialogFragment().show(parentFragmentManager, "RulesErrorDialogFragment")
                    }
                }
            }
        }
        return binding.root
    }

    private suspend fun checkLogin(): Boolean {
        with(binding) {
            return when {
                login.isBlank() -> {
                    loginInputLayout.error = "???????? ???? ?????????? ???????? ????????????"
                    false
                }
                login.length < 4 -> {
                    loginInputLayout.error = "?????????????? ???????????????? ?????? ????????????????????????"
                    false
                }
                isUserExist() -> {
                    loginInputLayout.error = "?????????? ???????????????????????? ????????????????????"
                    false
                }
                else -> {
                    loginInputLayout.error = null
                    true
                }
            }
        }
    }

    private suspend fun isUserExist(): Boolean {
        val userDto: Deferred<UserDto?> = lifecycleScope.async {
            vm.repository.getUser(login)
        }
        return userDto.await() != null
    }

    private fun checkMail(): Boolean {
        with(binding) {
            return when {
                email.isBlank() -> {
                    emailInputLayout.error = "???????? ???? ?????????? ???????? ????????????"
                    false
                }
                email.length < 6 -> {
                    emailInputLayout.error = "???????????????????????? ????????????????"
                    false
                }
                else -> {
                    emailInputLayout.error = null
                    true
                }
            }
        }
    }

    private suspend fun checkPassword(): Boolean {
        with(binding) {
            return when {
                password.isBlank() -> {
                    passwordInputLayout.error = "???????? ???? ?????????? ???????? ????????????"
                    false
                }
                password.length < 4 -> {
                    passwordInputLayout.error = "?????????????????????????? ???????????????????? ????????????????"
                    false
                }
                else -> {
                    passwordInputLayout.error = null
                    true
                }
            }
        }
    }

    private fun checkSecondPassword(): Boolean {
        with(binding) {
            return when {
                repeatPassword.isBlank() -> {
                    repeatPasswordInputLayout.error = "???????? ???? ?????????? ???????? ????????????"
                    false
                }
                password != repeatPassword -> {
                    repeatPasswordInputLayout.error = "???????? ???? ??????????????????"
                    false
                }
                else -> {
                    repeatPasswordInputLayout.error = null
                    true
                }
            }
        }
    }
}
