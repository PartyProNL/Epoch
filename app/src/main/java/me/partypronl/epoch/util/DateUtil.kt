package me.partypronl.epoch.util

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
}