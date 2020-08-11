package online.litterae.worldnewsin.model

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import online.litterae.worldnewsin.application.MyApplication
import online.litterae.worldnewsin.utils.NetworkUtils.Companion.TAG
import java.lang.Exception
import kotlin.collections.ArrayList

const val CURRENT_LANGUAGE = "Current language"
const val CURRENT_VIDEO_LIST = "Current video list"
const val MY_SHARED_PREFERENCES = "My shared preferences"

class SettingsManager {
    val availableChannels = AvailableChannels()

    fun getLanguages(): List<String> {
        return availableChannels.getLanguages()
    }

    fun getCurrentLanguage() : String {
        val preferences = MyApplication.context.getSharedPreferences(MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val currentLanguage = preferences.getString(CURRENT_LANGUAGE, LANGUAGE_ENGLISH)
        currentLanguage?.let {
            return currentLanguage
        }
        return LANGUAGE_ENGLISH
    }

    fun saveCurrentLanguage(language: String) {
        val preferences = MyApplication.context.getSharedPreferences(MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(CURRENT_LANGUAGE, language)
        editor.apply()
    }

    fun getChannelId(language: String, default: Boolean): String? {
        if (default) {
            return availableChannels.getChannelsMenu()[language]?.get(0)?.channelId
        } else throw Exception("Custom channel setting not yet implemented")
    }
}