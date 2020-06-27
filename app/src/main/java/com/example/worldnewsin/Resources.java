package com.example.worldnewsin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Resources {
    public static final String TAG = "MyTag";

    public static final String CURRENT_LANGUAGE = "Current language";
    public static final String CURRENT_VIDEO_LIST = "Current video list";

    public static final String LANGUAGE_ENGLISH = "English";
    public static final String LANGUAGE_FRENCH = "French";
    public static final String LANGUAGE_GERMAN = "German";
    public static final String LANGUAGE_GREEK = "Greek";
    public static final String LANGUAGE_HINDI = "Hindi";
    public static final String LANGUAGE_ITALIAN = "Italian";
    public static final String LANGUAGE_PERSIAN = "Persian";
    public static final String LANGUAGE_RUSSIAN = "Russian";
    public static final String LANGUAGE_TURKISH = "Turkish";
    public static final String LANGUAGE_URDU = "Urdu";

    static YouTubeChannel CHANNEL_BBC_ENGLISH = new YouTubeChannel(LANGUAGE_ENGLISH, "BBC News", "UC16niRr50-MSBwiO3YDb3RA");
    static YouTubeChannel CHANNEL_BBC_AFRIQUE = new YouTubeChannel(LANGUAGE_FRENCH, "BBC Afrique", "UCBte7YLdJx-O_YljuvN6whg");
    static YouTubeChannel CHANNEL_DW_DEUTSCH = new YouTubeChannel(LANGUAGE_GERMAN, "DW Deutsch", "UCMIgOXM2JEQ2Pv2d0_PVfcg");
    static YouTubeChannel CHANNEL_EURONEWS_GREEK = new YouTubeChannel(LANGUAGE_GREEK, "euronews (στα ελληνικά)", "UC8HJZNfDPoySgW9DN3YGNaA");
    static YouTubeChannel CHANNEL_BBC_HINDI = new YouTubeChannel(LANGUAGE_HINDI, "BBC News Hindi", "UCN7B-QD0Qgn2boVH5Q0pOWg");
    static YouTubeChannel CHANNEL_EURONEWS_ITALIANO = new YouTubeChannel(LANGUAGE_ITALIAN, "euronews (in Italiano)", "UC1mX9vuLOYf8fhaXS_KcDRg");
    static YouTubeChannel CHANNEL_BBC_PERSIAN = new YouTubeChannel(LANGUAGE_PERSIAN, "BBC Persian", "UCHZk9MrT3DGWmVqdsj5y0EA");
    static YouTubeChannel CHANNEL_BBC_RUSSIAN = new YouTubeChannel(LANGUAGE_RUSSIAN, "BBC News - Русская служба", "UC8zQiuT0m1TELequJ5sp5zw");
    static YouTubeChannel CHANNEL_BBC_TURKISH = new YouTubeChannel(LANGUAGE_TURKISH, "BBC News Türkçe", "UCeMQiXmFNTtN3OHlNJxnnUw");
    static YouTubeChannel CHANNEL_NEWS18_URDU = new YouTubeChannel(LANGUAGE_URDU, "News18 Urdu", "UC9IQhNgS43lmUdU-KdGyb0A");

    static Map<String, ArrayList<YouTubeChannel>> channelsMenu = new HashMap<>();
    static {
        channelsMenu.put(LANGUAGE_ENGLISH, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_ENGLISH)));
        channelsMenu.put(LANGUAGE_FRENCH, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_AFRIQUE)));
        channelsMenu.put(LANGUAGE_GERMAN, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_DW_DEUTSCH)));
        channelsMenu.put(LANGUAGE_GREEK, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_EURONEWS_GREEK)));
        channelsMenu.put(LANGUAGE_HINDI, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_HINDI)));
        channelsMenu.put(LANGUAGE_ITALIAN, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_EURONEWS_ITALIANO)));
        channelsMenu.put(LANGUAGE_PERSIAN, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_PERSIAN)));
        channelsMenu.put(LANGUAGE_RUSSIAN, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_RUSSIAN)));
        channelsMenu.put(LANGUAGE_TURKISH, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_BBC_TURKISH)));
        channelsMenu.put(LANGUAGE_URDU, new ArrayList<YouTubeChannel>(Arrays.asList(CHANNEL_NEWS18_URDU)));
    }

    private static class YouTubeChannel {
        private String language;
        private String name;
        private String channelId;

        public YouTubeChannel(String language, String name, String channelId) {
            this.language = language;
            this.name = name;
            this.channelId = channelId;
        }

        public String getChannelId() {
            return channelId;
        }
    }

    public static class Settings {
        static private String currentLanguage;
        static private List<YouTubeChannel> currentChannels = new ArrayList<>();
        static private SimpleVideoList currentVideoList;
        static void setCurrentLanguage (String language) {
            currentLanguage = language;
            currentChannels.clear();
            currentChannels.add(channelsMenu.get(currentLanguage).get(0));
        }

        public static String getCurrentLanguage() {
            return currentLanguage;
        }

        static void setCurrentVideoList (SimpleVideoList list) {
            currentVideoList = list;
        }
        static String getCurrentChannelId () {
            return currentChannels == null ? "" : currentChannels.get(0).getChannelId();
        }
        public static SimpleVideoList getCurrentVideoList() {
            return currentVideoList;
        }

        public static void saveSettings (Activity activity) {
            SharedPreferences storeData = activity.getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = storeData.edit();
            String videoListJSON = new Gson().toJson(Resources.Settings.getCurrentVideoList());
            Log.d(TAG, "Saving settings. videoListJSON: " + videoListJSON);
            editor.putString(CURRENT_VIDEO_LIST, videoListJSON);
            editor.putString(CURRENT_LANGUAGE, currentLanguage);
            editor.apply();
        }

        public static void restoreSettings (Activity activity) {
            SharedPreferences storedData = activity.getPreferences(MODE_PRIVATE);
            currentLanguage = storedData.getString(CURRENT_LANGUAGE, LANGUAGE_ENGLISH);
            String videoListJSON = storedData.getString(CURRENT_VIDEO_LIST, null);
            Log.d(TAG, "Restoring settings. videoListJSON: " + videoListJSON);
            currentVideoList = new Gson().fromJson(videoListJSON, SimpleVideoList.class);
        }
    }
}