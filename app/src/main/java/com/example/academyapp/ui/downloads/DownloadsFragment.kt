package com.example.academyapp.ui.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentDownloadsBinding
import com.example.academyapp.databinding.FragmentFavoriteBinding
import com.example.academyapp.domain.local.entity.TrackDto
import com.example.academyapp.ui.favorite.FavoriteTrackRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async

@AndroidEntryPoint
class DownloadsFragment:Fragment() {

    val vm: DownloadsVM by viewModels()
    private lateinit var binding: FragmentDownloadsBinding
    var recyclerAdapter: DownloadsTrackRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        binding.downloadsToolbar.setupWithNavController(findNavController(), appBarConfig)

        val res = binding.downloadsTracksRecycler
        recyclerAdapter = DownloadsTrackRecyclerAdapter(::clickOnTrack,::deleteTrack)
        res.adapter = recyclerAdapter

        lifecycleScope.launchWhenResumed {

            val items = lifecycleScope.async {
                vm.getTracks()
            }
            recyclerAdapter?.setItems(items.await())
        }
        return binding.root
    }

    private fun deleteTrack(track: TrackDto) {
        lifecycleScope.launchWhenResumed {
            vm.repository.deleteTrack(track.trackName,track.singerName)
            val items = lifecycleScope.async {
                vm.getTracks()
            }
            recyclerAdapter?.setItems(items.await())
        }
    }

    private fun clickOnTrack(track: TrackDto) {
        val bundle = Bundle()
        bundle.putParcelable("track", track)
        findNavController().navigate(R.id.action_favoriteFragment_to_trackInfoFragment, bundle)
    }
}