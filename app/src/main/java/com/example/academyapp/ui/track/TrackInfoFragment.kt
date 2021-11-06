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
import android.media.MediaPlayer
import android.media.AudioManager
import android.media.MediaPlayer.OnPreparedListener
import android.R

@AndroidEntryPoint
class TrackInfoFragment : Fragment() {

    private lateinit var binding: FragmentTrackinfoBinding
    val vm: TrackInfoVM by viewModels()
    private lateinit var mMediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackinfoBinding.inflate(inflater, container, false)
        initMediaPlayer()
        val track = arguments?.getParcelable<TrackResponse>("track")
        binding.trackNameInfotextView.text = track?.title
        binding.singerNameInfotextView.text = track?.artistResponse?.name
        Glide.with(binding.root.context)
            .load(track?.albumResponse?.coverXl)
            .centerCrop()
            .into(binding.albumInfoimageView)

        if (mMediaPlayer != null) {
            binding.playImageView.setOnClickListener {
                if (!mMediaPlayer.isPlaying) {
                    mMediaPlayer.start()
                    binding.playImageView.setImageResource(R.drawable.ic_media_pause)
                    try {
                        mMediaPlayer.setDataSource(
                            track?.preview
                        )
                        mMediaPlayer.prepareAsync()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    mMediaPlayer.pause()
                    binding.playImageView.setImageResource(R.drawable.ic_media_play)
                }
            }
        }

        return binding.root
    }

    fun initMediaPlayer() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer.setOnPreparedListener(OnPreparedListener { togglePlayPause() })
    }

    private fun togglePlayPause() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
            //  mPlayerControl.setImageResource(R.drawable.ic_play)
        } else {
            mMediaPlayer.start()
            //  mPlayerControl.setImageResource(R.drawable.ic_pause)
        }
    }
}