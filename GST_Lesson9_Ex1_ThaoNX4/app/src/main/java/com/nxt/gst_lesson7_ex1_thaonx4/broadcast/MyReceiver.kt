package com.nxt.gst_lesson7_ex1_thaonx4.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nxt.gst_lesson7_ex1_thaonx4.service.MyService

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val actionMusic = intent?.getIntExtra("action", 0)

        val intentService = Intent(context, MyService::class.java)
        intentService.putExtra("action_music",actionMusic)

        context?.startService(intentService)
    }
}