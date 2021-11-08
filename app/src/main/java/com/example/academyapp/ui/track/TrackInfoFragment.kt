package com.example.academyapp.ui.track

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentTrackinfoBinding
import com.example.academyapp.domain.api.responses.TrackResponse
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.bumptech.glide.load.resource.bitmap.CenterCrop

import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.cancelAndJoin
import okhttp3.internal.wait


@AndroidEntryPoint
class TrackInfoFragment : Fragment() {

    val vm: TrackInfoVM by viewModels()
    var track: TrackResponse? = null
    private lateinit var binding: FragmentTrackinfoBinding
    private lateinit var mMediaPlayer: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrackinfoBinding.inflate(inflater, container, false)
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfig)
        track = arguments?.getParcelable("track")
        initMediaPlayer()
        setupTrackInfo()
        setupProgressBar()
        setupPlayButton()

        return binding.root
    }

    private fun initMediaPlayer() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mMediaPlayer.setDataSource(track?.musicFile)
            mMediaPlayer.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupTrackInfo() {
        with(binding) {
            trackNameInfotextView.text = track?.title
            singerNameInfotextView.text = track?.artistResponse?.name

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
            Glide.with(root.context)
                .load(track?.albumResponse?.coverXl)
                .centerCrop()
                .apply(requestOptions)
                .into(albumInfoimageView)
        }
    }

    private fun setupProgressBar() {
        with(binding.trackProgressBar) {
            this.max = mMediaPlayer.duration
            this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        mMediaPlayer.seekTo(p1)
                        binding.zeroTextView.text = formatTime(mMediaPlayer.currentPosition)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }

        with(binding) {
            mMediaPlayer.setOnCompletionListener {
                playImageView.setImageResource(R.drawable.ic_play)
                trackProgressBar.progress = 0
                checkCurrentTime(false)
            }
            endTextView.text = formatTime(mMediaPlayer.duration)

            rewindTrackImageView.setOnClickListener {
                mMediaPlayer.seekTo(mMediaPlayer.currentPosition - 5000)
            }
            forwardTrackImageView.setOnClickListener {
                mMediaPlayer.seekTo(mMediaPlayer.currentPosition + 5000)
            }
        }
    }

    private fun setupPlayButton() {
        binding.playImageView.setOnClickListener {
            if (!mMediaPlayer.isPlaying) {
                mMediaPlayer.start()
                binding.playImageView.setImageResource(R.drawable.ic_pause)
                checkCurrentTime(true)
            } else {
                mMediaPlayer.pause()
                checkCurrentTime(false)
                binding.playImageView.setImageResource(R.drawable.ic_play)
            }
        }
    }

    private fun checkCurrentTime(state: Boolean) {
        val job = lifecycleScope.launchWhenResumed {
            repeat(10000) {
                binding.trackProgressBar.progress = mMediaPlayer.currentPosition
                binding.zeroTextView.text = formatTime(mMediaPlayer.currentPosition)
                delay(500)
            }
        }
        if (state) {
            job.start()
        } else {
            job.cancel()
        }
    }

    private fun formatTime(time: Int): String {
        val formatter: DateFormat = SimpleDateFormat("mm:ss", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date(time.toLong()))
    }
}