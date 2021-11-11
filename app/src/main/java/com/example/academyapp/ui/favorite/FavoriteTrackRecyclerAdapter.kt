package com.example.academyapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.ItemTrackBinding
import com.example.academyapp.domain.local.entity.TrackDto

class FavoriteTrackRecyclerAdapter(
    private val tracks: List<TrackDto>,
    val click: (TrackDto) -> Unit
) : RecyclerView.Adapter<FavoriteTrackRecyclerAdapter.TrackViewHolder>() {

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

        fun bind(track: TrackDto) {
            binding.nameTrackTextView.text = track.trackName
            binding.singerTextView.text = track.singerName
            Glide.with(binding.root.context)
                .load(track.id)
                .centerCrop()
                .into(binding.albumImageView)
            binding.trackLayout.setOnClickListener {
                click.invoke(track)
            }
        }
    }
}
