package online.litterae.worldnewsin.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import online.litterae.worldnewsin.R
import online.litterae.worldnewsin.utils.NetworkUtils
import java.util.ArrayList
import java.util.HashMap

/**
 * Adapter for the video list. Manages a set of YouTubeThumbnailViews, including initializing each
 * of them only once and keeping track of the loader of each one. When the ListFragment gets
 * destroyed it releases all the loaders.
 */
class VideoListAdapter(context: Context?, private val entries: List<VideoEntry>) : BaseAdapter() {
    private val entryViews: List<View>
    private val thumbnailViewToLoaderMap: MutableMap<YouTubeThumbnailView, YouTubeThumbnailLoader>
    private val inflater: LayoutInflater
    private val thumbnailListener: ThumbnailListener
    private var labelsVisible: Boolean

    init {
        entryViews = ArrayList()
        thumbnailViewToLoaderMap = HashMap()
        inflater = LayoutInflater.from(context)
        thumbnailListener = ThumbnailListener()
        labelsVisible = true
    }

    fun releaseLoaders() {
        for (loader in thumbnailViewToLoaderMap.values) {
            loader.release()
        }
    }

    fun setLabelVisibility(visible: Boolean) {
        labelsVisible = visible
        for (view in entryViews) {
            view.findViewById<View>(R.id.text).visibility =
                if (visible) View.VISIBLE else View.GONE
        }
    }

    override fun getCount(): Int {
        return entries.size
    }

    override fun getItem(position: Int): VideoEntry {
        return entries[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view = convertView
        val entry = entries[position]

        // There are three cases here
        if (view == null) {
            // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
            view = inflater.inflate(R.layout.video_list_item, parent, false)
            val thumbnail =
                view.findViewById<View>(R.id.thumbnail) as YouTubeThumbnailView
            thumbnail.tag = entry.videoId
            thumbnail.initialize(NetworkUtils.API_KEY, thumbnailListener)
        } else {
            val thumbnail =
                view.findViewById<View>(R.id.thumbnail) as YouTubeThumbnailView
            val loader = thumbnailViewToLoaderMap[thumbnail]
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                thumbnail.tag = entry.videoId
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                thumbnail.setImageResource(R.drawable.loading_thumbnail)
                loader.setVideo(entry.videoId)
            }
        }
        val label = view!!.findViewById<View>(R.id.text) as TextView
        label.text = entry.text
        label.visibility = if (labelsVisible) View.VISIBLE else View.GONE
        val date = view.findViewById<TextView>(R.id.date)
        date.text = entry.date
        return view
    }

    private inner class ThumbnailListener :
        YouTubeThumbnailView.OnInitializedListener,
        YouTubeThumbnailLoader.OnThumbnailLoadedListener {
        override fun onInitializationSuccess(
            view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader
        ) {
            loader.setOnThumbnailLoadedListener(this)
            thumbnailViewToLoaderMap[view] = loader
            view.setImageResource(R.drawable.loading_thumbnail)
            val videoId = view.tag as String
            loader.setVideo(videoId)
        }

        override fun onInitializationFailure(
            view: YouTubeThumbnailView, loader: YouTubeInitializationResult
        ) {
            view.setImageResource(R.drawable.no_thumbnail)
        }

        override fun onThumbnailLoaded(
            view: YouTubeThumbnailView,
            videoId: String
        ) {
        }

        override fun onThumbnailError(
            view: YouTubeThumbnailView,
            errorReason: YouTubeThumbnailLoader.ErrorReason
        ) {
            view.setImageResource(R.drawable.no_thumbnail)
        }
    }
}