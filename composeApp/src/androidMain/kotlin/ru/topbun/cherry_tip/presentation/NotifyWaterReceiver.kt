package ru.topbun.cherry_tip.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.reminder_drink_water_descr
import cherrytip.composeapp.generated.resources.reminder_drink_water_title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import ru.topbun.cherry_tip.R

class NotifyWaterReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                val notificationManager = getSystemService(it, NotificationManager::class.java) as NotificationManager
                createNotificationChannel(notificationManager)
                val notification = NotificationCompat.Builder(it, CHANNEL_ID)
                    .setContentTitle(getString(Res.string.reminder_drink_water_title))
                    .setContentText(getString(Res.string.reminder_drink_water_descr))
                    .setSmallIcon(R.drawable.ic_water)
                    .build()
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }

    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {

        private const val CHANNEL_ID = "reminder_water_id"
        private const val CHANNEL_NAME = "reminder_water"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent {
            return Intent(context, NotifyWaterReceiver::class.java)
        }
    }

}