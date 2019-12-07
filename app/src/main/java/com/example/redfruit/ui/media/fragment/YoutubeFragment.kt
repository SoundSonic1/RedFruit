package com.example.redfruit.ui.media.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.redfruit.R
import com.example.redfruit.ui.base.DaggerFullScreenFragment
import com.example.redfruit.util.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject
import kotlinx.android.synthetic.main.youtube_fragment.*

class YoutubeFragment : DaggerFullScreenFragment() {

    @Inject
    lateinit var youtubeId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.youtube_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (youtubeId.isNotBlank()) {
            youtubePlayerView.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeId, 0f)
                    }
                }
            )
        }

        lifecycle.addObserver(youtubePlayerView)
    }

    companion object {
        /**
         * Pass the Youtube Id to the new fragment instance
         */
        fun newInstance(youtubeId: String) = YoutubeFragment().apply {
            arguments = bundleOf(Constants.YOUTUBE_ID_KEY to youtubeId)
        }
    }
}
