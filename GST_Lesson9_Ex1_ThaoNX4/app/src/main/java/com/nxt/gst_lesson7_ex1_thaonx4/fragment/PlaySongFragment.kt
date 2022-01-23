package com.nxt.gst_lesson7_ex1_thaonx4.fragment

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nxt.gst_lesson7_ex1_thaonx4.DataProvider
import com.nxt.gst_lesson7_ex1_thaonx4.service.MyService
import com.nxt.gst_lesson7_ex1_thaonx4.click.PlaySong
import com.nxt.gst_lesson7_ex1_thaonx4.R
import com.nxt.gst_lesson7_ex1_thaonx4.databinding.FragmentPlaySongBinding
import java.text.SimpleDateFormat


class PlaySongFragment(var position: Int) : Fragment(), PlaySong {

    private var _binding: FragmentPlaySongBinding? = null
    private val binding get() = _binding!!
    private var myService: MyService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaySongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startService(position)

        click()
    }

    @SuppressLint("SimpleDateFormat")
    fun startService(position: Int) {

        val intent = Intent(context, MyService::class.java)
        intent.putExtra("position", position)
        requireContext().startService(intent)

        binding.imgPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
        binding.seekbar.max = DataProvider.getDuration(requireContext(), position) / 1000
        binding.seekbar.progress = 0
        val sdf = SimpleDateFormat("mm:ss")
        binding.timeSong.text = (sdf.format(DataProvider.getDuration(requireContext(), position)))
    }

    private fun click() {
        binding.imgPlayOrPause.setOnClickListener {
            playOrPause()
        }

        binding.imgNext.setOnClickListener {
            nextClick()
            Toast.makeText(requireContext(), "Next", Toast.LENGTH_SHORT).show()
        }
        binding.imgBack.setOnClickListener {
            backClick()
            Toast.makeText(requireContext(), "Back", Toast.LENGTH_SHORT).show()
        }

        /**    lifecycleScope.launch(Dispatchers.IO) {
        if (myService != null) {
        val mCurrentTime: Int = myService!!.getCurrentPosition() / 1000
        val sdf = SimpleDateFormat("mm:ss")
        withContext(Dispatchers.Main) {
        binding.seekbar.progress = mCurrentTime
        binding.currentTime.text = sdf.format(myService!!.getCurrentPosition())
        }
        }
        delay(300)
        }
         */

        requireActivity().runOnUiThread(object : Runnable {
            @SuppressLint("SimpleDateFormat")
            override fun run() {
                if (myService != null) {
                    val mCurrentTime: Int = myService!!.getCurrentPosition() / 1000
                    binding.seekbar.progress = mCurrentTime
                    val sdf = SimpleDateFormat("mm:ss")
                    binding.currentTime.text = sdf.format(myService!!.getCurrentPosition())
                }
                Handler().postDelayed(this, 300)
            }
        })
    }

    private fun bindToService() {
        val intent = Intent(context, MyService::class.java)
        context?.bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            myService = (iBinder as MyService.MyBinder).instance
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            myService = null
        }
    }

    override fun playOrPause() {
        if (myService!!.isPlaying()) {
            myService!!.pause()
            binding.imgPlayOrPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            Toast.makeText(requireContext(), "Pause", Toast.LENGTH_SHORT).show()
        } else {
            myService!!.start()
            binding.imgPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
            Toast.makeText(requireContext(), "Resume", Toast.LENGTH_SHORT).show()
        }
    }

    override fun backClick() {
        myService!!.backSong()
        binding.imgPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
    }

    override fun nextClick() {
        binding.imgPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
        myService!!.nextSong()
    }

    override fun onResume() {
        super.onResume()
        bindToService()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}