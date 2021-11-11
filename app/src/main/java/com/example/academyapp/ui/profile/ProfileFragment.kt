package com.example.academyapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.academyapp.MainActivity
import com.example.academyapp.R
import com.example.academyapp.RegistrationActivity
import com.example.academyapp.databinding.FragmentProfileBinding
import com.example.academyapp.databinding.FragmentTrackinfoBinding
import com.example.academyapp.domain.Repository
import com.example.academyapp.domain.local.UserDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    val vm: ProfileVM by viewModels()
    private lateinit var binding: FragmentProfileBinding
    var password = ""
    var email = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenResumed {
           var login = vm.repository.getLogin()
            val user = login?.let { vm.repository.getUser(it) }
            with(binding) {
                loginProfileTextView.text = "Логин: ${user?.login}"
                passwordProfileTextView.text = "Пароль: ${user?.password}"
                emailProfileTextView.text = "Email: ${user?.email}"

            }
        }
        binding.logOutButton.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                vm.repository.putSession(false)
                startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
            }
        }

        return binding.root
    }
}