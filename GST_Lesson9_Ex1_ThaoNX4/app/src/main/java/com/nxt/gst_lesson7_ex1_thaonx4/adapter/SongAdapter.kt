package com.nxt.gst_lesson7_ex1_thaonx4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nxt.gst_lesson7_ex1_thaonx4.click.OnItemClick
import com.nxt.gst_lesson7_ex1_thaonx4.Song
import com.nxt.gst_lesson7_ex1_thaonx4.databinding.SongBinding

class SongAdapter(val clickItem: OnItemClick) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var binding: SongBinding? = null

    private var differCallBack = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
        binding = SongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {
       val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(var binding: SongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.apply {
                tvTitle.text = song.title
                tvArtist.text = song.artist
                ivItemImage.setImageResource(song.image)
                itemView.setOnClickListener {
                    clickItem.itemClick(adapterPosition)
                }
            }
        }
    }

}