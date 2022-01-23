package com.nxt.gst_lesson7_ex1_thaonx4.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.nxt.gst_lesson7_ex1_thaonx4.R
import com.nxt.gst_lesson7_ex1_thaonx4.Song
import com.nxt.gst_lesson7_ex1_thaonx4.activity.MainActivity

object NotificationMusicManager {

    /**
     * keep updating control on notification music
     */



    private const val CHANNEL_ID = "channel_id"
    private const val CHANNEL = "channel"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.setSound(null, null)
            val manager = context.getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendNotification(context: Context, song: Song): Notification {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bitmap = (ResourcesCompat.getDrawable(context.resources,
            song.image,
            null) as BitmapDrawable).toBitmap()

        val remoteViews = RemoteViews(context.packageName, R.layout.custom_notifi)

        remoteViews.setTextViewText(R.id.title_notification_song, song.title)
        remoteViews.setTextViewText(R.id.title_notification_artist, song.artist)
        remoteViews.setImageViewBitmap(R.id.img_song_notification, bitmap)

        remoteViews.setImageViewResource(R.id.img_play_or_pause, R.drawable.ic_baseline_pause_24)

        /** if (isPlaying) {
        remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,
        getPendingIntent(context, ACTION_PAUSE))
        remoteViews.setImageViewResource(R.id.img_play_or_pause,
        R.drawable.ic_baseline_pause_24)
        } else {
        remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,
        getPendingIntent(context, ACTION_RESUME))
        remoteViews.setImageViewResource(R.id.img_play_or_pause,
        R.drawable.ic_baseline_play_arrow_24)
        } */

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setSound(null)
            .build()
    }
/**
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(mContext: Context, action: Int): PendingIntent {

        val intent = Intent(mContext, MyReceiver::class.java)
        intent.putExtra("action", action)

        return PendingIntent.getBroadcast(mContext.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }*/
}