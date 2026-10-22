package com.viktoriia.trippocket.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RescheduleReceiver : BroadcastReceiver() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface RescheduleEntryPoint {
        fun rescheduler(): NotificationRescheduler
    }

    override fun onReceive(context: Context, intent: Intent) {
        val rescheduler = EntryPointAccessors
            .fromApplication(
                context.applicationContext,
                RescheduleEntryPoint::class.java
            )
            .rescheduler()

        val pending = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                rescheduler.rescheduleAll()
            } finally {
                pending.finish()
            }
        }
    }
}