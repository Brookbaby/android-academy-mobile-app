package com.example.academyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val tracks = listOf(
            Track(trackName = "Money",singerName = "Инстасамка"),
            Track(trackName = "Mo",singerName = "Инстасамкаaaa", trackDownloaded = true),
            Track(trackName = "Moneyyy",singerName = "Инст"),
            Track(trackName = "Moooney",singerName = "самка", trackDownloaded = true)
        )
        binding.tracksRecyclerView.adapter = MusicTrackRecyclerAdapter(tracks) { clickOnTrack() }

        /*binding.buttonLogOut.setOnClickListener {
            (requireActivity() as MainActivity).prefs?.edit()?.putBoolean("session", false)?.apply()
            startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
        }*/
        return binding.root

    }

    private fun clickOnTrack() {
        findNavController().navigate(R.id.action_mainFragment_to_trackInfoFragment)
    }
}
