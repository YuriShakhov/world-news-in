package online.litterae.worldnewsin

import android.provider.Settings
import online.litterae.worldnewsin.model.LANGUAGE_ENGLISH
import online.litterae.worldnewsin.model.LANGUAGE_GERMAN
import online.litterae.worldnewsin.model.LANGUAGE_URDU
import online.litterae.worldnewsin.model.SettingsManager
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun channelIdTest() {
        val settingsManager = SettingsManager()
        assertEquals(settingsManager.getChannelId(LANGUAGE_ENGLISH, true), "UC16niRr50-MSBwiO3YDb3RA")
        assertEquals(settingsManager.getChannelId(LANGUAGE_GERMAN, true), "UCMIgOXM2JEQ2Pv2d0_PVfcg")
        assertEquals(settingsManager.getChannelId(LANGUAGE_URDU, true), "UC9IQhNgS43lmUdU-KdGyb0A")
    }
}