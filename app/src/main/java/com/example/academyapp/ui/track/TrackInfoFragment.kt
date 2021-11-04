package com.example.academyapp.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.academyapp.databinding.FragmentTrackinfoBinding
import com.example.academyapp.domain.api.responses.TrackResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackInfoFragment : Fragment() {

    private lateinit var binding: FragmentTrackinfoBinding
    val vm: TrackInfoVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrackinfoBinding.inflate(inflater,container,false)
        val track = arguments?.getParcelable<TrackResponse>("track")
        binding.trackNameInfotextView.text = track?.title
        binding.singerNameInfotextView.text = track?.artistResponse?.name
        Glide.with(binding.root.context)
            .load(track?.albumResponse?.coverXl)
            .centerCrop()
            .into(binding.albumInfoimageView)
        return binding.root
    }
}