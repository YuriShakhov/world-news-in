package com.example.worldnewsin;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.worldnewsin.utils.NetworkUtils;
import com.example.worldnewsin.utils.YoutubeDataAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Presenter {
    private static final String TAG = "MyTag";

    private MainActivity view;

    private SimpleVideoList videoList;

    void attachView(MainActivity view) {
        this.view = view;
    }
    void detachView() {
        view = null;
    }

    void getList(String language) {
        Log.d(TAG, "getList: OK");
        Resources.Settings.setCurrentLanguage(language);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.YOUTUBE_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YoutubeDataAPIService youtubeDataAPIService = retrofit.create(YoutubeDataAPIService.class);
        Call<SimpleVideoList> call = youtubeDataAPIService
                .getSimpleVideosList(
                        NetworkUtils.API_KEY,
                        Resources.Settings.getCurrentChannelId(),
                        "items(id,snippet(title,publishedAt))",
                        "snippet,id",
                        "date",
                        "10"
                );
        call.enqueue(new Callback<SimpleVideoList>() {
            @Override
            public void onResponse(Call<SimpleVideoList> call, Response<SimpleVideoList> response) {
                Log.d(TAG, "onResponse: OK");
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Code: " + response.code());
                    return;
                }
                videoList = response.body();
                Resources.Settings.setCurrentVideoList(videoList);
                setNewVideoList();
            }

            @Override
            public void onFailure(Call<SimpleVideoList> call, Throwable t) {
                Log.d(TAG, "Failure: " + t.getMessage());
            }
        });
    }

    void setNewVideoList() {
        List<MainActivity.VideoEntry> list = new ArrayList<>();
        videoList = Resources.Settings.getCurrentVideoList();
        if (videoList == null) return;
        for (SimpleVideo video : videoList.items) {
            MainActivity.VideoEntry entry = new MainActivity
                    .VideoEntry(video.getTitle(), video.getVideoId(), video.getPublishedAt());
            list.add(entry);
            Log.d(TAG, "onResponse: " + video.getTitle() + "  " + video.getVideoId() + "  " + video.getPublishedAt());
            Log.d(TAG, "entry: " + entry.toString());
        }
        Log.d(TAG, "list: " + list.toString());
        Log.d(TAG, "view: " + view.toString());
        view.setVideosList(list);
    }
}
