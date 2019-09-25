package com.example.redfruit.ui.media.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.redfruit.R
import com.example.redfruit.ui.base.DaggerFullScreenFragment
import com.example.redfruit.util.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject
import javax.inject.Provider

class StreamVideoFragment : DaggerFullScreenFragment() {

    @Inject
    lateinit var exoPlayerProvider: Provider<ExoPlayer>
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val url = arguments?.getString(STREAM_VIDEO_KEY) ?: ""

        val view = inflater.inflate(R.layout.stream_video_fragment, container, false)
        val playerView = view.findViewById<PlayerView>(R.id.exoPlayerViewFragment)

        exoPlayer = exoPlayerProvider.get()
        exoPlayer.playWhenReady = true
        playerView.player = exoPlayer

        val userAgent = Util.getUserAgent(requireContext(), Constants.USER_AGENT)

        if (url.isNotBlank()) {
            val mediaSource = DashMediaSource
                .Factory(DefaultDataSourceFactory(requireContext(), userAgent))
                .createMediaSource(Uri.parse(url))
            exoPlayer.prepare(mediaSource)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    companion object {

        private const val STREAM_VIDEO_KEY = "STREAM_VIDEO_KEY"

        fun newInstance(url: String): StreamVideoFragment {
            val fragment = StreamVideoFragment()
            fragment.arguments = bundleOf(STREAM_VIDEO_KEY to url)
            return fragment
        }
    }

}
