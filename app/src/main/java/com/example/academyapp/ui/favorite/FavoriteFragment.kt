package com.example.academyapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentFavoriteBinding
import com.example.academyapp.domain.api.responses.TrackResponse
import com.example.academyapp.domain.local.entity.TrackDto
import com.example.academyapp.ui.main.MusicTrackRecyclerAdapter
import com.example.academyapp.ui.profile.ProfileVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    val vm: FavoriteVM by viewModels()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)

        lifecycleScope.launchWhenResumed {
            val items = vm.getTracks()
            binding.favoriteTracksRecycler.adapter =
                FavoriteTrackRecyclerAdapter(items) {track -> clickOnTrack(track) }
        }

        return binding.root
    }

    private fun clickOnTrack(track: TrackDto) {
        var bundle = Bundle()
        bundle.putParcelable("track",track)
        findNavController().navigate(R.id.action_favoriteFragment_to_trackInfoFragment,bundle)
}
}