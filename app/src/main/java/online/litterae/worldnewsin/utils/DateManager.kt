package online.litterae.worldnewsin.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TODAY = "Today"
const val DATE_YESTERDAY = "Yesterday"
const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DAY_FORMAT = "dd MMMM yyyy"
const val HOUR_FORMAT = "HH:mm"

class DateManager {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: String): String {
            val youtubeFormat = SimpleDateFormat(DATE_FORMAT)
            val myDayFormat = SimpleDateFormat(DAY_FORMAT)
            val myHourFormat = SimpleDateFormat(HOUR_FORMAT)
            val videoDate = youtubeFormat.parse(date)
            val todayCalendar = Calendar.getInstance()
            todayCalendar[Calendar.HOUR_OF_DAY] = 0
            todayCalendar[Calendar.MINUTE] = 0
            todayCalendar[Calendar.SECOND] = 0
            todayCalendar[Calendar.MILLISECOND] = 0
            val today = todayCalendar.time
            val yesterday = Date()
            yesterday.time = today.time - 1000.toLong() * 60 * 60 * 24
            videoDate?.let {
                val dayString = if (!videoDate.before(today)) {
                    DATE_TODAY
                } else if (!videoDate.before(yesterday)) {
                    DATE_YESTERDAY
                } else {
                    myDayFormat.format(videoDate)
                }
                val hourString = myHourFormat.format(videoDate)
                return "$dayString, $hourString"
            }
            return date
        }
    }
}