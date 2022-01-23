package com.nxt.gst_lesson7_ex1_thaonx4.service


import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.nxt.gst_lesson7_ex1_thaonx4.DataProvider
import com.nxt.gst_lesson7_ex1_thaonx4.click.PlaySong
import com.nxt.gst_lesson7_ex1_thaonx4.Song
import com.nxt.gst_lesson7_ex1_thaonx4.service.NotificationMusicManager.sendNotification
import java.util.ArrayList

class MyService : Service(), MediaPlayer.OnCompletionListener {

    companion object {
        const val ACTION_PAUSE = 1
        const val ACTION_RESUME = 2
    }


    private var mBinder: IBinder = MyBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var mListSong: ArrayList<Song> = DataProvider.listSong
    private var position = -1
    private var mPlaySongListener: PlaySong? = null
    //----------

   // private var isPlaying: Boolean = false
    private var mSong: Song? = null

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    inner class MyBinder : Binder() {
        val instance: MyService
            get() = this@MyService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val sPosition = intent.getIntExtra("position", -1)
        if (sPosition != -1) {
            playSong(sPosition)
        }

        NotificationMusicManager.createNotificationChannel(this)

        val song = DataProvider.listSong[position]

        Log.d("song", song.toString())

        val notification: Notification = sendNotification(this, song)

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    private fun startMusic(position: Int) {
        mediaPlayer = MediaPlayer.create(applicationContext, mListSong[position].uri)
        mediaPlayer!!.start()
    }


    private fun playSong(mPosition: Int) {
        position = mPosition
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            startMusic(position)
        } else {
            startMusic(position)
        }
    }

    private fun onCompleted() {
        mediaPlayer!!.setOnCompletionListener(this)
    }

    override fun onCompletion(p0: MediaPlayer?) {
        if (mPlaySongListener != null) {
            mPlaySongListener!!.nextClick()
        }
        startMusic(position)
        onCompleted()
    }

    fun start() {
        mediaPlayer!!.start()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer!!.isPlaying
    }

    fun pause() {
        mediaPlayer!!.pause()
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer!!.currentPosition
    }

    fun backSong() {
        position = if (position - 1 < 0) mListSong.size - 1 else position - 1
        playSong(position)
    }

    fun nextSong() {
        position = (position + 1) % mListSong.size
        playSong(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}