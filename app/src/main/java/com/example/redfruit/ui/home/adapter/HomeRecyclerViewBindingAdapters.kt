package com.example.redfruit.ui.home.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.util.Constants
import com.example.redfruit.util.SizableColorDrawable
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ortiz.touchview.TouchImageView

/**
 * Collect all HomeRecyclerViewBindingAdapters here
 */
object HomeRecyclerViewBindingAdapters {
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
    @BindingAdapter("youtubeThumbnail")
    fun loadYoutubeThumbnail(imageView: ImageView, url: String) {
       if (url.isNotBlank()) {
           imageView.load(url) {
               crossfade(true)
           }
       }
    }

    @JvmStatic
    @BindingAdapter("imageFragment")
    fun loadImageToFragment(imageView: TouchImageView, url: String) {
        if (url.isNotBlank()) {
            imageView.load(url) {
                crossfade(true)
            }
        }
    }
}