package ru.icomplex.dentistry.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationSettings @Inject constructor(
    @ApplicationContext val appContext: Context
) {

    companion object {
        const val NAME = "DENTISTRY_NOTIFY"
        const val ID = "AUTH_NOTIFY"
        const val DESCRIPTION = "AUTH_NOTIFY_CHANNEL"
    }

    fun getNotificationManager(): NotificationManager {
        var notifyManager: NotificationManager? = null

        if (notifyManager == null) {
            notifyManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        var mChannel = notifyManager.getNotificationChannel(ID)
        if (mChannel == null) {
            mChannel = createAuthChannel()
            notifyManager.createNotificationChannel(mChannel)
        }
        return notifyManager
    }

    private fun createAuthChannel(): NotificationChannel {
        return NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = DESCRIPTION
            enableVibration(true)
            lightColor = Color.GREEN
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }
    }
}