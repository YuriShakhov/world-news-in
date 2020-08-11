package online.litterae.worldnewsin.model

import java.util.*
import kotlin.collections.ArrayList

const val LANGUAGE_ENGLISH = "English"
const val LANGUAGE_FRENCH = "French"
const val LANGUAGE_GERMAN = "German"
const val LANGUAGE_GREEK = "Greek"
const val LANGUAGE_HINDI = "Hindi"
const val LANGUAGE_ITALIAN = "Italian"
const val LANGUAGE_PERSIAN = "Persian"
const val LANGUAGE_RUSSIAN = "Russian"
const val LANGUAGE_TURKISH = "Turkish"
const val LANGUAGE_URDU = "Urdu"

class AvailableChannels {

    companion object {
        var CHANNEL_BBC_ENGLISH =
            YouTubeChannel(
                LANGUAGE_ENGLISH,
                "BBC News",
                "UC16niRr50-MSBwiO3YDb3RA"
            )
        var CHANNEL_BBC_AFRIQUE =
            YouTubeChannel(
                LANGUAGE_FRENCH,
                "BBC Afrique",
                "UCBte7YLdJx-O_YljuvN6whg"
            )
        var CHANNEL_DW_DEUTSCH =
            YouTubeChannel(
                LANGUAGE_GERMAN,
                "DW Deutsch",
                "UCMIgOXM2JEQ2Pv2d0_PVfcg"
            )
        var CHANNEL_EURONEWS_GREEK =
            YouTubeChannel(
                LANGUAGE_GREEK,
                "euronews (στα ελληνικά)",
                "UC8HJZNfDPoySgW9DN3YGNaA"
            )
        var CHANNEL_BBC_HINDI =
            YouTubeChannel(
                LANGUAGE_HINDI,
                "BBC News Hindi",
                "UCN7B-QD0Qgn2boVH5Q0pOWg"
            )
        var CHANNEL_EURONEWS_ITALIANO =
            YouTubeChannel(
                LANGUAGE_ITALIAN,
                "euronews (in Italiano)",
                "UC1mX9vuLOYf8fhaXS_KcDRg"
            )
        var CHANNEL_BBC_PERSIAN =
            YouTubeChannel(
                LANGUAGE_PERSIAN,
                "BBC Persian",
                "UCHZk9MrT3DGWmVqdsj5y0EA"
            )
        var CHANNEL_BBC_RUSSIAN =
            YouTubeChannel(
                LANGUAGE_RUSSIAN,
                "BBC News - Русская служба",
                "UC8zQiuT0m1TELequJ5sp5zw"
            )
        var CHANNEL_BBC_TURKISH =
            YouTubeChannel(
                LANGUAGE_TURKISH,
                "BBC News Türkçe",
                "UCeMQiXmFNTtN3OHlNJxnnUw"
            )
        var CHANNEL_NEWS18_URDU =
            YouTubeChannel(
                LANGUAGE_URDU,
                "News18 Urdu",
                "UC9IQhNgS43lmUdU-KdGyb0A"
            )

        val channelsMenu: MutableMap<String, ArrayList<YouTubeChannel>> = HashMap()

        init {
            channelsMenu[LANGUAGE_ENGLISH] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_ENGLISH
                ))
            channelsMenu[LANGUAGE_FRENCH] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_AFRIQUE
                ))
            channelsMenu[LANGUAGE_GERMAN] = ArrayList(
                Arrays.asList(
                    CHANNEL_DW_DEUTSCH
                ))
            channelsMenu[LANGUAGE_GREEK] = ArrayList(
                Arrays.asList(
                    CHANNEL_EURONEWS_GREEK
                ))
            channelsMenu[LANGUAGE_HINDI] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_HINDI
                ))
            channelsMenu[LANGUAGE_ITALIAN] = ArrayList(
                Arrays.asList(
                    CHANNEL_EURONEWS_ITALIANO
                ))
            channelsMenu[LANGUAGE_PERSIAN] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_PERSIAN
                ))
            channelsMenu[LANGUAGE_RUSSIAN] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_RUSSIAN
                ))
            channelsMenu[LANGUAGE_TURKISH] = ArrayList(
                Arrays.asList(
                    CHANNEL_BBC_TURKISH
                ))
            channelsMenu[LANGUAGE_URDU] = ArrayList(
                Arrays.asList(
                    CHANNEL_NEWS18_URDU
                ))
        }
    }

    fun getLanguages(): List<String> {
        return channelsMenu.keys.toList()
    }

    fun getChannelsMenu(): Map<String, ArrayList<YouTubeChannel>> {
        return channelsMenu
    }
}
