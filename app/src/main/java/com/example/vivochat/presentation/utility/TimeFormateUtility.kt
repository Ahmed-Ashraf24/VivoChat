package com.example.vivochat.presentation.utility

import java.util.Date

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
}