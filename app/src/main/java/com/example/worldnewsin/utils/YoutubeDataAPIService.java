package com.example.worldnewsin.utils;

import com.example.worldnewsin.SimpleVideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeDataAPIService {
    @GET("search")
    Call<SimpleVideoList> getSimpleVideosList(
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("fields") String fields,
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults
     );
}
