package online.litterae.worldnewsin.view

import android.os.Bundle
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import online.litterae.worldnewsin.utils.NetworkUtils

class VideoBoxFragment : YouTubePlayerFragment(), YouTubePlayer.OnInitializedListener {
    private var player: YouTubePlayer? = null
    private var videoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(NetworkUtils.API_KEY, this)
    }

    override fun onDestroy() {
        if (player != null) {
            player!!.release()
        }
        super.onDestroy()
    }

    fun setVideoId(videoId: String?) {
        if (videoId != null && videoId != this.videoId) {
            this.videoId = videoId
            if (player != null) {
                player!!.cueVideo(videoId)
            }
        }
    }

    fun pause() {
        if (player != null) {
            player!!.pause()
        }
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        restored: Boolean
    ) {
        this.player = player
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
        player.setOnFullscreenListener(activity as MainActivity)
        if (!restored && videoId != null) {
            player.cueVideo(videoId)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        result: YouTubeInitializationResult
    ) {
        player = null
    }

    companion object {
        fun newInstance(): VideoBoxFragment {
            return VideoBoxFragment()
        }
    }
}
