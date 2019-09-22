package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.redfruit.R
import com.example.redfruit.ui.base.BaseFullScreenFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeFragment : BaseFullScreenFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val youtubeView = inflater.inflate(R.layout.youtube_fragment, container, false)
        val youtubeId = arguments?.getString(YOUTUBE_FRAGMENT_URL_KEY) ?: ""

        val youtubePlayerView =
            youtubeView.findViewById<YouTubePlayerView>(R.id.youtubePlayerViewFragment)

        lifecycle.addObserver(youtubePlayerView)

        if (youtubeId.isNotBlank()) {
            youtubePlayerView.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(youtubeId, 0f)
                    }
                }
            )
        }

        disableToolbar(activity!!)

        return youtubeView
    }

    override fun onPause() {
        super.onPause()
        enableToolbar(activity!!)
    }

    companion object {
        private const val YOUTUBE_FRAGMENT_URL_KEY = "YOUTUBE_FRAGMENT_URL_KEY"

        fun newInstance(youtubeId: String): YoutubeFragment{
            val fragment = YoutubeFragment()
            fragment.arguments = bundleOf(YOUTUBE_FRAGMENT_URL_KEY to youtubeId)
            return fragment
        }
    }
}
