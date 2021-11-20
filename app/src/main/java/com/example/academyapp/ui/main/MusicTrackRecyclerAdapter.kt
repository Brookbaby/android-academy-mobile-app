package com.example.academyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.ItemTrackBinding
import com.example.academyapp.domain.api.responses.TrackResponse

class MusicTrackRecyclerAdapter(
    private val tracks: List<TrackResponse>,
    val openTrack: (TrackResponse) -> Unit,
    val addTrackToDb: (TrackResponse) -> Unit,
    val deleteTrack: (TrackResponse) -> Unit
) :
    RecyclerView.Adapter<MusicTrackRecyclerAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
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
            Glide.with(binding.root.context)
                .load(track.albumResponse.cover)
                .centerCrop()
                .into(binding.albumImageView)
            binding.trackLayout.setOnClickListener {
                openTrack.invoke(track)
            }
            var isFavoriteTrack = false
            binding.notFavoriteImageView.setOnClickListener {
                val favorite = binding.notFavoriteImageView
                if (isFavoriteTrack == false) {
                    favorite.setImageResource(R.drawable.ic_favorite_track)
                    isFavoriteTrack = true
                    addTrackToDb.invoke(track)
                } else {
                    favorite.setImageResource(R.drawable.ic_not_favorite)
                    isFavoriteTrack = false
                    deleteTrack.invoke(track)
                }
            }
            binding.downloadImageView.setOnClickListener {
                binding.downloadImageView.isInvisible = true
                addTrackToDb.invoke(track)
            }
        }
    }
}