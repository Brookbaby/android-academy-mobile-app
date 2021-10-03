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

class LoginFragment : Fragment() {

    lateinit var user: User
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        var registrationActivity = requireActivity() as RegistrationActivity
        binding.loginButton.setOnClickListener {
            var login = binding.loginEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            lifecycleScope.launchWhenResumed {
                user = registrationActivity.userDao.getUser(login)
            }.invokeOnCompletion {
                if (password == user.password) {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                } else {
                    binding.passwordInputLayout.error = "Неверный пароль"
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}