package online.litterae.worldnewsin.view

//import androidx.fragment.app.ListFragment
import android.app.ListFragment
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.video_list_item.view.*
import online.litterae.worldnewsin.R
import online.litterae.worldnewsin.model.SimpleVideoList
import online.litterae.worldnewsin.utils.NetworkUtils.Companion.TAG
import online.litterae.worldnewsin.viewmodel.MyViewModel

class MainActivity : AppCompatActivity(), OnFullscreenListener {

    lateinit var listFragment: VideoListFragment
    lateinit var videoBoxFragment: VideoBoxFragment
    private var isFullscreen = false

    lateinit var model: MyViewModel
    lateinit var videoListData: LiveData<SimpleVideoList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MyViewModel::class.java)

        videoListData = model.getVideoListData()
        videoListData.observe(this, object : Observer<SimpleVideoList> {
            override fun onChanged(videoList: SimpleVideoList) {
                val list: MutableList<VideoEntry> =
                    java.util.ArrayList()
//                videoList = Resources.Settings.getCurrentVideoList()
//                if (videoList == null) return
                for (video in videoList.items) {
                    val entry = VideoEntry(
                        video.title,
                        video.videoId,
                        video.publishedAt
                    )
                    list.add(entry)
                    Log.d(TAG, "onResponse: " + video.title + "  " + video.videoId + "  " + video.publishedAt
                    )
                    Log.d(TAG,"entry: $entry")
                }
                Log.d(TAG,"list: $list")
                setVideosList(list)
            }
        })

        listFragment = getFragmentManager().findFragmentById(R.id.listFragment) as VideoListFragment
        videoBoxFragment = getFragmentManager().findFragmentById(R.id.videoFragment) as VideoBoxFragment

        videoBox.setVisibility(View.INVISIBLE)
        layout()
        checkYouTubeApi()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Resources.Settings.saveSettings(this)
    }

    private fun init() {
        Log.e(TAG, "init: ${model.languages}")
        val languagesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, model.languages
        )
        languagesList.adapter = languagesAdapter
        val spinnerPosition = languagesAdapter.getPosition(model.currentLanguage)
        Log.e(TAG, "spinnerPosition: $spinnerPosition")
        languagesList.setSelection(spinnerPosition)
        languagesList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                Log.e(TAG, "view.text == null: ${view.text == null}, model.languages[spinnerPosition] == null: ${model.languages[spinnerPosition] == null}")
                val selectedLanguage = model.languages[position]
                Log.e(TAG, "selectedLanguage: $selectedLanguage")
                model.renewList(selectedLanguage)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun setVideosList(list: List<VideoEntry>) {
        listFragment.setList(list)
        listFragment.renewAdapter()
        Log.d(TAG, "setVideosList: OK")
    }

    private fun checkYouTubeApi() {
        val errorReason = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this)
        if (errorReason.isUserRecoverableError) {
            errorReason.getErrorDialog(this,
                RECOVERY_DIALOG_REQUEST
            ).show()
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            val errorMessage = String.format(getString(R.string.error_player), errorReason.toString())
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        } else Log.d(TAG, "checkYouTubeApi: OK")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        layout()
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        this.isFullscreen = isFullscreen
        layout()
    }

    /**
     * Sets up the layout programatically for the three different states. Portrait, landscape or
     * fullscreen+landscape. This has to be done programmatically because we handle the orientation
     * changes ourselves in order to get fluent fullscreen transitions, so the xml layout resources
     * do not get reloaded.
     */
    private fun layout() {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        listFragment.view?.setVisibility(if (isFullscreen) View.GONE else View.VISIBLE) //VideoListFragment doesn't extend view
        listFragment.setLabelVisibility(isPortrait)
        closeButton.visibility = if (isPortrait) View.VISIBLE else View.GONE
        if (isFullscreen) {
            videoBox.translationY = 0f // Reset any translation that was applied in portrait.
            setLayoutSize(
                videoBoxFragment.view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setLayoutSizeAndGravity(
                videoBox,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.TOP or Gravity.START
            )
        } else if (isPortrait) {
            setLayoutSize(
                listFragment.view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setLayoutSize(
                videoBoxFragment.view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setLayoutSizeAndGravity(
                videoBox,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
        } else {
            videoBox.translationY = 0f // Reset any translation that was applied in portrait.
            val screenWidth = dpToPx(resources.configuration.screenWidthDp)
            setLayoutSize(
                listFragment.view,
                screenWidth / 4,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            val videoWidth =
                screenWidth - screenWidth / 4 - dpToPx(LANDSCAPE_VIDEO_PADDING_DP)
            setLayoutSize(
                videoBoxFragment.view,
                videoWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setLayoutSizeAndGravity(
                videoBox, videoWidth, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.END or Gravity.CENTER_VERTICAL
            )
        }
    }

    fun onClickClose(view: View?) {
        listFragment.listView.clearChoices()
        listFragment.listView.requestLayout()
        videoBoxFragment.pause()
        val animator = videoBox.animate()
            .translationYBy(videoBox.height.toFloat())
            .setDuration(ANIMATION_DURATION_MILLIS.toLong())
        runOnAnimationEnd(animator, Runnable { videoBox.visibility = View.INVISIBLE })
    }

    private fun runOnAnimationEnd(
        animator: ViewPropertyAnimator,
        runnable: Runnable
    ) {
        animator.withEndAction(runnable)
    }

    // Utility methods for layouting.
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    companion object {
        /** The duration of the animation sliding up the video in portrait.  */
        private const val ANIMATION_DURATION_MILLIS = 300

        /** The padding between the video list and the video in landscape orientation.  */
        private const val LANDSCAPE_VIDEO_PADDING_DP = 5

        /** The request code when calling startActivityForResult to recover from an API service error.  */
        private const val RECOVERY_DIALOG_REQUEST = 1
        private fun setLayoutSize(view: View?, width: Int, height: Int) {
            val params = view!!.layoutParams
            params.width = width
            params.height = height
            view.layoutParams = params
        }

        private fun setLayoutSizeAndGravity(
            view: View?,
            width: Int,
            height: Int,
            gravity: Int
        ) {
            val params =
                view!!.layoutParams as FrameLayout.LayoutParams
            params.width = width
            params.height = height
            params.gravity = gravity
            view.layoutParams = params
        }
    }

    class VideoListFragment : ListFragment() {
        private lateinit var pageAdapter: VideoListAdapter
        private lateinit var videoBox: View

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            pageAdapter = VideoListAdapter(
                activity,
                VIDEO_LIST
            )
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            videoBox = activity.findViewById<View>(R.id.videoBox)
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
            listAdapter = pageAdapter
        }

        override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
            val videoId = VIDEO_LIST[position].videoId
            val videoFragment = fragmentManager?.findFragmentById(R.id.videoFragment) as VideoBoxFragment?
            videoFragment?.setVideoId(videoId)

            // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
            if (videoBox.visibility != View.VISIBLE) {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // Initially translate off the screen so that it can be animated in from below.
                    videoBox.translationY = videoBox.height.toFloat()
                }
                videoBox.visibility = View.VISIBLE
            }

            // If the fragment is off the screen, we animate it in.
            if (videoBox.translationY > 0) {
                videoBox.animate().translationY(0f).duration = ANIMATION_DURATION_MILLIS.toLong()
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            pageAdapter.releaseLoaders()
        }

        fun setList(newList: List<VideoEntry>) {
            VIDEO_LIST.clear()
            VIDEO_LIST.addAll(newList)
        }

        fun renewAdapter() {pageAdapter =
            VideoListAdapter(
                activity,
                VIDEO_LIST
            )
            listAdapter = pageAdapter
        }

        fun setLabelVisibility(visible: Boolean) {
            pageAdapter.setLabelVisibility(visible)
        }

        companion object {
            private val VIDEO_LIST: MutableList<VideoEntry> =
                ArrayList()
        }
    }
}