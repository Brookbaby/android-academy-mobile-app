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
            lifecycleScope.launchWhenResumed {
                registrationActivity.userDao.addUser(user)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}