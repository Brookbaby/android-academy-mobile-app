package com.example.academyapp.ui.downloads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.ItemTrackBinding
import com.example.academyapp.domain.local.entity.TrackDto

class DownloadsTrackRecyclerAdapter(
    val click: (TrackDto) -> Unit,
    val delete: (TrackDto) -> Unit
) : RecyclerView.Adapter<DownloadsTrackRecyclerAdapter.TrackTTViewHolder>() {

    private val tracks: MutableList<TrackDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackTTViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackTTViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackTTViewHolder, position: Int) {
        val item = tracks[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun setItems(newTracks: List<TrackDto>) {
        tracks.clear()
        tracks.addAll(newTracks)
        notifyDataSetChanged()
    }

    inner class TrackTTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding by viewBinding(ItemTrackBinding::bind)

        fun bind(track: TrackDto, position: Int) {
            binding.nameTrackTextView.text = track.trackName
            binding.singerTextView.text = track.singerName
            binding.downloadImageView.setImageResource(R.drawable.ic_delete)
            binding.notFavoriteImageView.isInvisible = true
            binding.downloadImageView.setOnClickListener {
                delete.invoke(track)
            }
            Glide.with(binding.root.context)
                .load(track.cover)
                .centerCrop()
                .into(binding.albumImageView)
            binding.trackLayout.setOnClickListener {
                click.invoke(track)
            }
        }
    }
}