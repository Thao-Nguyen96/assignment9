package com.nxt.gst_lesson7_ex1_thaonx4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nxt.gst_lesson7_ex1_thaonx4.R
import com.nxt.gst_lesson7_ex1_thaonx4.databinding.ActivityMainBinding
import com.nxt.gst_lesson7_ex1_thaonx4.fragment.PlaySongFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun clickItem(position: Int){
        val playingFragment =supportFragmentManager.findFragmentById(R.id.fcvPlaySong) as? PlaySongFragment

        if (playingFragment != null){
            playingFragment.startService(position)
        }else{
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fcvPlaySong,
                    PlaySongFragment(position),
                    null
                )
                .commit()
        }
    }
}