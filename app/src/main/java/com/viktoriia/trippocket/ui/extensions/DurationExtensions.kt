package com.viktoriia.trippocket.ui.extensions

import android.content.Context
import com.viktoriia.trippocket.R
import java.time.Duration

fun Duration.toTripDurationText(
    context: Context
): String {
    val totalMinutes = toMinutes()
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> {
            val hoursText = context.resources.getQuantityString(
                R.plurals.transport_duration_hours,
                hours.toInt(),
                hours.toInt()
            )

            val minutesText = context.resources.getQuantityString(
                R.plurals.transport_duration_minutes,
                minutes.toInt(),
                minutes.toInt()
            )

            "$hoursText $minutesText"
        }

        hours > 0 -> {
            context.resources.getQuantityString(
                R.plurals.transport_duration_hours,
                hours.toInt(),
                hours.toInt()
            )
        }

        else -> {
            context.resources.getQuantityString(
                R.plurals.transport_duration_minutes,
                minutes.toInt(),
                minutes.toInt()
            )
        }
    }
}