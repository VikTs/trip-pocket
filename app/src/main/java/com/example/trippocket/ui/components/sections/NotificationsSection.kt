package com.example.trippocket.ui.components.sections

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.trippocket.R

@Composable
fun NotificationsSection(
    title: String,
    content: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var notificationsEnabled by remember {
        mutableStateOf(
            NotificationManagerCompat
                .from(context)
                .areNotificationsEnabled()
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                notificationsEnabled =
                    NotificationManagerCompat
                        .from(context)
                        .areNotificationsEnabled()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    if (notificationsEnabled) {
        content?.invoke()
    } else {
        NotificationsDisabled(context)
    }
}

@Composable
fun NotificationsDisabled(context: Context) {
    fun openNotifications() {
        val intent = Intent(
            Settings.ACTION_APP_NOTIFICATION_SETTINGS
        ).apply {
            putExtra(
                Settings.EXTRA_APP_PACKAGE,
                context.packageName
            )
        }

        context.startActivity(intent)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            stringResource(
                R.string.notifications_disabled_message
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = ::openNotifications,
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primary
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            Text(
                text = stringResource(
                    R.string.notifications_enable_btn_label
                ),
            )
        }
    }
}