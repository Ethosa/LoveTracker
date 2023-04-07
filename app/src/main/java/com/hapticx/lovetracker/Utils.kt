package com.hapticx.lovetracker

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.runtime.Stable
import java.text.SimpleDateFormat
import java.util.Date


@Stable
class Utils {
    companion object {
        const val COUPLE_DATE = "CoupleDate"

        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        /**
         * Loads saved dating date if available
         */
        fun getDate(preferences: SharedPreferences): Date? {
            val date = preferences.getString(COUPLE_DATE, null)
            var result: Date? = null
            date?.let {
                result = dateFormat.parse(date)!!
            }
            return result
        }
    }
}