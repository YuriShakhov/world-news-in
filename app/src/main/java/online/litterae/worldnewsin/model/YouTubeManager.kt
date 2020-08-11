package online.litterae.worldnewsin.model

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import online.litterae.worldnewsin.utils.NetworkUtils
import online.litterae.worldnewsin.utils.YoutubeDataAPIService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class YouTubeManager {
    fun loadVideos(channelId: String?): Single<SimpleVideoList> {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetworkUtils.YOUTUBE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val youtubeDataAPIService: YoutubeDataAPIService = retrofit.create<YoutubeDataAPIService>(
            YoutubeDataAPIService::class.java
        )

        return youtubeDataAPIService.getSimpleVideosList(
                NetworkUtils.API_KEY,
                channelId,
                "items(id,snippet(title,publishedAt))",
                "snippet,id",
                "date",
                "10")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}