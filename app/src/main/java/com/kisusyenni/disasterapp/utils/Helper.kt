package com.kisusyenni.disasterapp.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(dateString: String): String {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val newPattern = "EEE, dd MMM yyyy HH:mm"
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val inputFormatter =
            DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern(newPattern, Locale.getDefault())
        val date = LocalDateTime.parse(dateString, inputFormatter)
        outputFormatter.format(date)
    } else {
        val inputFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(newPattern, Locale.getDefault())
        val date: Date = inputFormat.parse(dateString) as Date
        outputFormat.format(date)
    }
}