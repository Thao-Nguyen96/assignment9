package com.nxt.gst_lesson7_ex1_thaonx4.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nxt.gst_lesson7_ex1_thaonx4.DataProvider
import com.nxt.gst_lesson7_ex1_thaonx4.activity.MainActivity
import com.nxt.gst_lesson7_ex1_thaonx4.click.OnItemClick
import com.nxt.gst_lesson7_ex1_thaonx4.adapter.SongAdapter
import com.nxt.gst_lesson7_ex1_thaonx4.databinding.FragmentListSongBinding


class ListSongFragment : Fragment(), OnItemClick {

    private var _binding: FragmentListSongBinding? = null
    private val binding get() = _binding!!
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(this)
        binding.rvSong.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                false)
            adapter = songAdapter
            addItemDecoration(object : DividerItemDecoration(
                context, LinearLayout.VERTICAL
            ) {})
        }
        DataProvider.getData(requireContext())
        songAdapter.differ.submitList(DataProvider.listSong)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun itemClick(position: Int) {
        (activity as MainActivity).clickItem(position)
    }
}