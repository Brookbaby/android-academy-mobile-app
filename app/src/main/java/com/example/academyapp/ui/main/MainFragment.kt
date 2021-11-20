package com.example.academyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentMainBinding
import com.example.academyapp.domain.api.responses.TrackResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    val vm: MainVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfig)

        lifecycleScope.launchWhenResumed {
            try {
                val items = vm.getTracks()
                binding.tracksRecyclerView.adapter =
                    MusicTrackRecyclerAdapter(items.trackResponses, ::clickOnTrack, ::addTrackToDb, ::deleteTrack)
                binding.tracksRecyclerView.isInvisible = false
                binding.errorTextView.isVisible = false
                binding.progressBar.isVisible = false
            } catch (e: Exception) {
                binding.tracksRecyclerView.isVisible = false
                binding.errorTextView.isVisible = true
                binding.errorTextView.text = e.message
                binding.progressBar.isVisible = false
            }
        }
        return binding.root

    }

    private fun clickOnTrack(track: TrackResponse) {
        val bundle = Bundle()
        bundle.putParcelable("track", track)
        findNavController().navigate(R.id.action_mainFragment_to_trackInfoFragment, bundle)
    }

    private fun addTrackToDb(track: TrackResponse) {
        lifecycleScope.launchWhenResumed {
            vm.repository.addTrack(track)
        }
    }

    private fun deleteTrack(track: TrackResponse) {
        lifecycleScope.launchWhenResumed {
            vm.repository.deleteTrack(track.title, track.artistResponse.name)
        }
    }
}
