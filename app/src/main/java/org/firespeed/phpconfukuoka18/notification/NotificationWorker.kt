package org.firespeed.phpconfukuoka18.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.model.OrmaHolder


class NotificationWorker : Worker() {
    @Suppress("DEPRECATION")
    override fun doWork(): Worker.WorkerResult {
        val sessionId = inputData.getInt(ARGS_SESSION_ID, 0)

        val session = OrmaHolder.ORMA.selectFromSession().idEq(sessionId).singleOrNull()
                ?: return WorkerResult.FAILURE
        if (!session.favorite) {
            return WorkerResult.SUCCESS
        }

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "chanel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = applicationContext.getString(R.string.channel_title)
            val notifyDescription = applicationContext.getString(R.string.channel_description)

            // Channelの取得と生成
            if (notificationManager.getNotificationChannel(id) == null) {
                val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                channel.apply {
                    description = notifyDescription
                }
                notificationManager.createNotificationChannel(channel)
            }

        }
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationCompat.Builder(applicationContext, id) else NotificationCompat.Builder(applicationContext)
        val notification = notificationBuilder.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentText(session.location)
            setContentTitle(applicationContext.getString(R.string.session_format, session.title))
        }.build()
        notificationManager.notify(0, notification)

        return WorkerResult.SUCCESS
    }

    companion object {
        const val ARGS_SESSION_ID = "sessionId"
        fun setArgument(sessionId: Int): Data {
            return Data.Builder().putInt(ARGS_SESSION_ID, sessionId).build()
        }
    }


}