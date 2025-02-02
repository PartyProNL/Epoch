package me.partypronl.epoch.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.util.Calendar

object DateUtil {
    fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60)) % 24
        val days = (millis / (1000 * 60 * 60 * 24))

        val parts = mutableListOf<String>()

        if (days > 0) parts.add("${days}d")
        if (hours > 0) parts.add("${hours}h")
        if (minutes > 0) parts.add("${minutes}m")
        if (seconds > 0) parts.add("${seconds}s")

        return parts.joinToString(" ").ifEmpty { "0s" }
    }

    /**
     * Takes a date and adds one day
     * @param date The date to add one day too
     * @return The date with one day added, in milliseconds
     */
    fun getNextDay(date: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1)
        return calendar.timeInMillis
    }

    /**
     * Get the current date
     * Sets the date to the start of the day
     * @return Current date in milliseconds as a long
     */
    fun getToday(): Long {
        val todayInMillis = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        return todayInMillis
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val afterTodaySelectableDates = object : SelectableDates {
        val today = getToday()

        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis > today
        }
    }
}