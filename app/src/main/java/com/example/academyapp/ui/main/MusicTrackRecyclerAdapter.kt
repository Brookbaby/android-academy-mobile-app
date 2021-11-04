package com.example.academyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.ItemTrackBinding
import com.example.academyapp.domain.api.responses.AlbumResponse
import com.example.academyapp.domain.api.responses.TrackResponse
import kotlin.random.Random.Default.nextBoolean
import retrofit2.Response

class MusicTrackRecyclerAdapter(
    private val tracks: List<TrackResponse>,
    var click: (TrackResponse) -> Unit
) :
    RecyclerView.Adapter<MusicTrackRecyclerAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = tracks[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding by viewBinding(ItemTrackBinding::bind)

        fun bind(track: TrackResponse) {
            binding.nameTrackTextView.text = track.title
            binding.singerTextView.text = track.artistResponse.name
            binding.downloadImageView.isVisible = nextBoolean()
            Glide.with(binding.root.context)
                .load(track.albumResponse.cover)
                .centerCrop()
                .into(binding.albumImageView)
            binding.trackLayout.setOnClickListener {
               click.invoke(track)
            }
        }
    }
}