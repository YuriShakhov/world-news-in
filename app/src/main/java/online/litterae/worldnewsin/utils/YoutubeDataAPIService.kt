package online.litterae.worldnewsin.utils

import io.reactivex.rxjava3.core.Single
import online.litterae.worldnewsin.model.SimpleVideoList
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeDataAPIService {
    @GET("search")
    fun getSimpleVideosList(
        @Query("key") key: String?,
        @Query("channelId") channelId: String?,
        @Query("fields") fields: String?,
        @Query("part") part: String?,
        @Query("order") order: String?,
        @Query("maxResults") maxResults: String?
    ): Single<SimpleVideoList>
}