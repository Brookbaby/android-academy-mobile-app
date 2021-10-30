package com.example.academyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.academyapp.R
import com.example.academyapp.databinding.ItemTrackBinding

class MusicTrackRecyclerAdapter(private val tracks: List<Track>) :
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

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding by viewBinding(ItemTrackBinding::bind)

        fun bind(track: Track) {
            //binding.albumImageView.setImageDrawable(track.albumPhoto)
            binding.nameTrackTextView.text = track.trackName
            binding.singerTextView.text = track.singerName
            binding.downloadImageView.isVisible = track.trackDownloaded
        }
    }
}