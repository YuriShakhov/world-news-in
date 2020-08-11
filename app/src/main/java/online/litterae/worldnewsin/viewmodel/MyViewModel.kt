package online.litterae.worldnewsin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import online.litterae.worldnewsin.model.SettingsManager
import online.litterae.worldnewsin.model.SimpleVideoList
import online.litterae.worldnewsin.model.YouTubeManager
import online.litterae.worldnewsin.utils.NetworkUtils.Companion.TAG

class MyViewModel : ViewModel() {
    val settingsManager = SettingsManager()
    val youTubeManager = YouTubeManager()

    val languages: List<String> = settingsManager.getLanguages()
    var currentLanguage: String = settingsManager.getCurrentLanguage()
    val videoListData: MutableLiveData<SimpleVideoList> = MutableLiveData()
    init {
        Log.e(TAG, "MyViewModel init. currentLanguage: $currentLanguage")
        renewList(currentLanguage)
    }

    fun getVideoListData(): LiveData<SimpleVideoList> {
        return videoListData
    }

    fun renewList(language: String) {
        currentLanguage = language
        val channelId = settingsManager.getChannelId(currentLanguage, true)
        Log.e(TAG, "renewList. currentLanguage: $currentLanguage, channelId: $channelId")
        youTubeManager.loadVideos(channelId)
            .subscribe(object : DisposableSingleObserver<SimpleVideoList>() {
                override fun onSuccess(t: SimpleVideoList) {
                    Log.e(TAG, "onSuccess: $t")
                    videoListData.postValue(t)
                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: " + e?.message.toString())
                }
            })
    }


    override fun onCleared() {
        settingsManager.saveCurrentLanguage(currentLanguage)
        super.onCleared()
    }
}