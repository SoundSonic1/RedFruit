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
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.android.synthetic.main.stream_video_fragment.*

class StreamVideoFragment : DaggerFullScreenFragment() {

    @Inject
    lateinit var url: String

    @Inject
    lateinit var exoPlayerProvider: Provider<ExoPlayer>
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.stream_video_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exoPlayer = exoPlayerProvider.get()
        exoPlayer.playWhenReady = true
        exoPlayerView.player = exoPlayer

        val userAgent = Util.getUserAgent(requireContext(), Constants.USER_AGENT)

        if (url.isNotBlank()) {
            val mediaSource = DashMediaSource
                .Factory(DefaultDataSourceFactory(requireContext(), userAgent))
                .createMediaSource(Uri.parse(url))
            exoPlayer.prepare(mediaSource)
        }
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

        /**
         * Pass the streaming url (dash) to the new fragment instance
         */
        fun newInstance(url: String) = StreamVideoFragment().apply {
            arguments = bundleOf(Constants.STREAM_URL_KEY to url)
        }
    }
}
