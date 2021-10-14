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
import com.example.academyapp.domain.local.entity.User
import dagger.hilt.android.AndroidEntryPoint

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
            var rulsIsChecked = binding.rulsSwitch.isChecked
            lifecycleScope.launchWhenResumed {
                if (validateLogin() && validateEmail() && validatePassword() && validateRepeatPassword()) {
                    if (rulsIsChecked) {
                        val user = User(login = login, password = password)
                        registrationActivity.userDao.addUser(user)
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

    private fun validateLogin(): Boolean {
        return when {
            login.isBlank() -> {
                binding.loginInputLayout.error = "Поле не может быть пустым"
                false
            }
            login.length < 4 -> {
                binding.loginInputLayout.error = "Слишком короткое имя пользователя"
                false
            }
            else -> {
                binding.loginInputLayout.error = null
                true
            }
        }
    }

    private fun validateEmail(): Boolean {
        return when {
            email.isBlank() -> {
                binding.emailInputLayout.error = "Поле не может быть пустым"
                false
            }
            email.length < 6 -> {
                binding.emailInputLayout.error = "Недостаточное количество симовлов"
                false
            }
            else -> {
                binding.emailInputLayout.error = null
                true
            }
        }
    }

    private fun validatePassword(): Boolean {

        return when {
            password.isBlank() -> {
                binding.passwordInputLayout.error = "Поле не может быть пустым"
                false
            }
            password.length < 4 -> {
                binding.passwordInputLayout.error = "Недостаточное количество символов"
                false
            }
            else -> {
                binding.passwordInputLayout.error = null
                true
            }
        }
    }

    private fun validateRepeatPassword(): Boolean {
        return when {
            repeatPassword.isBlank() -> {
                binding.repeatPasswordInputLayout.error = "Поле не может быть пустым"
                false
            }
            password != repeatPassword -> {
                binding.repeatPasswordInputLayout.error = "Поля не совпадают"
                false
            }
            else -> {
                binding.passwordInputLayout.error = null
                true
            }
        }
    }
}