package com.example.academyapp.ui.autorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.academyapp.databinding.DialogRulesErrorBinding

class RulesErrorDialogFragment : DialogFragment() {

    lateinit var binding: DialogRulesErrorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogRulesErrorBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}