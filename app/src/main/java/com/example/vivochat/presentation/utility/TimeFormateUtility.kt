package com.example.vivochat.presentation.utility

import com.google.firebase.Timestamp
import java.util.Date
import java.util.concurrent.TimeUnit

object TimeFormateUtility {
    fun formateTampToHour(timestamp: Long): String {

 val timeFormat = java.text.SimpleDateFormat("hh:mm a")
        val formattedTime = timeFormat.format(java.util.Date(timestamp))
        return formattedTime

    }

    fun formateTampToDayAndHour(timestamp: Long): String {

        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd hh:mm a")
        val formattedDate = sdf.format(Date(timestamp))

        return formattedDate

    }


    fun getRelativeTime(timestamp: Timestamp): String {
        val now = System.currentTimeMillis()
        val storyTime = timestamp.toDate().time
        val diff = now - storyTime

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            minutes < 1 -> "just now"
            minutes < 60 -> "${minutes}m"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            else -> java.text.SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate())
        }
    }
}