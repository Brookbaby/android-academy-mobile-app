package com.example.academyapp.ui.track

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.academyapp.R
import com.example.academyapp.databinding.FragmentTrackinfoBinding
import com.example.academyapp.domain.api.responses.TrackResponse
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay

@AndroidEntryPoint
class TrackInfoFragment : Fragment() {

    val vm: TrackInfoVM by viewModels()
    var track: TrackResponse? = null
    private lateinit var binding: FragmentTrackinfoBinding
    private lateinit var mMediaPlayer: MediaPlayer
    val job by lazy {
        lifecycleScope.launchWhenResumed {
            repeat(10000) {
                binding.trackProgressBar.progress = mMediaPlayer.currentPosition
                binding.zeroTextView.text = formatTime(mMediaPlayer.currentPosition)
                delay(500)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrackinfoBinding.inflate(inflater, container, false)
        track = arguments?.getParcelable("track")
        initMediaPlayer()
        initProgressBar()
        binding.trackNameInfotextView.text = track?.title
        binding.singerNameInfotextView.text = track?.artistResponse?.name
        Glide.with(binding.root.context)
            .load(track?.albumResponse?.coverXl)
            .centerCrop()
            .into(binding.albumInfoimageView)
        binding.pauseImageView.setOnClickListener {
            if (!mMediaPlayer.isPlaying) {
                mMediaPlayer.start()
                binding.pauseImageView.setImageResource(R.drawable.ic_pause)
                job.start()
            } else {
                mMediaPlayer.pause()
                job.cancel()
                binding.pauseImageView.setImageResource(R.drawable.ic_play)
            }
        }

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

    private fun initProgressBar() {
        with(binding.trackProgressBar) {
            this.max = mMediaPlayer.duration
            this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        mMediaPlayer.seekTo(p1)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }

        mMediaPlayer.setOnCompletionListener {
            binding.pauseImageView.setImageResource(R.drawable.ic_play)
            binding.trackProgressBar.progress = 0
            job.cancel()
        }
        binding.endTextView.text = formatTime(mMediaPlayer.duration)

    }

    private fun formatTime(time:Int):String{
        val formatter: DateFormat = SimpleDateFormat("mm:ss", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date(time.toLong()))
    }
}