package com.example.redfruit.ui.shared

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.util.Constants
import com.example.redfruit.util.SizableColorDrawable
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Collect all BindingAdapters here
 */
object BindingAdapters {
    /**
     * items might be null because the ViewModel which provides them is
     * not yet initialized
     */
    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: List<Post>?) {
        if (items != null && recyclerViewHome.adapter is HomeAdapter) {
            (recyclerViewHome.adapter as HomeAdapter).notifyChanges(items)
        }
    }

    @JvmStatic
    @BindingAdapter("imageSource")
    // ImageSource can be null
    fun loadImage(imageView: ImageView, image: ImageSource?) {
        image?.let {
            imageView.load(it.url) {
                crossfade(true)
                placeholder(SizableColorDrawable(0x222222, it.width, it.height))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("secureMedia")
    fun loadMedia(playerView: PlayerView, secureMedia: SecureMedia?) {
        secureMedia?.let {
            it.redditVideo?.let { video ->
                val exoPlayer = ExoPlayerFactory.newSimpleInstance(playerView.context,
                    DefaultTrackSelector())
                playerView.player = exoPlayer

                val userAgent = Util.getUserAgent(playerView.context, Constants.USER_AGENT)
                val mediaSource = ProgressiveMediaSource
                    .Factory(DefaultDataSourceFactory(playerView.context, userAgent))
                    .createMediaSource(Uri.parse(video.url))

                exoPlayer.prepare(mediaSource)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("youtube")
    fun loadYoutubeVideo(youtubePlayerView: YouTubePlayerView, youtubeId: String?) {
        if (!youtubeId.isNullOrBlank()) {
            val callback: YouTubePlayerCallback = object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(youtubeId, 0f)
                }
            }
            youtubePlayerView.getYouTubePlayerWhenReady(callback)
            youtubePlayerView.visibility = View.VISIBLE
        } else {
            youtubePlayerView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("toolbarImage")
    fun loadToolbarImage(imageView: ImageView, url: String?) {
        if (url.isNullOrBlank()) {
            imageView.load("https://wallpaperplay.com/walls/full/2/9/e/31137.jpg") {
                crossfade(true)
            }
        } else {
            imageView.load(url) {
                crossfade(true)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("iconImage")
    fun loadIconImage(imageView: ImageView, subredditAbout: SubredditAbout?) {
        subredditAbout?.let {
            // Not sure why there are two icons available
            val url = if (it.icon_img.isNotBlank()) {
                it.icon_img
            } else {
                it.community_icon
            }
            if (url.isBlank()) {
                imageView.load(R.drawable.ic_reddit_24dp) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            } else {
                imageView.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_reddit_24dp)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}