package com.nxt.gst_lesson7_ex1_thaonx4

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import java.io.Serializable

data class Song(val image: Int, val title: String, val artist: String, val uri: Uri) : Serializable

object DataProvider {

    val listSong = arrayListOf<Song>()

    fun getData(context: Context): ArrayList<Song> {

        listSong.add(Song(R.drawable.chidan, "Mất Trí Nhớ",
            "Chi Dân", getUri(context, R.raw.matrinho)))
        listSong.add(Song(R.drawable.noitinhyeubatdau, "Sài Gòn",
            "Vy Oanh", getUri(context, R.raw.saigon)))
        listSong.add(Song(R.drawable.aimo, "Ái Mộ",
            "Massew", getUri(context, R.raw.aimo)))
        listSong.add(Song(R.drawable.roitoiluon, "Rồi Tới Luôn",
            "Phát Hồ", getUri(context, R.raw.roitoiluon)))
        listSong.add(Song(R.drawable.toyeucau, "Tớ Yêu Cậu",
            "Phạm Đình Thái Ngân", getUri(context, R.raw.toyeucau)))
        listSong.add(Song(R.drawable.sontung, "Dịu Dàng Là Ngày Em Đến",
            "Erik", getUri(context, R.raw.diudangemden)))


        return listSong
    }


   private fun getUri(context: Context, path: Int): Uri {
        return Uri.parse("android.resource://" + context.packageName + "/" + path)
    }

    fun getDuration(context: Context, position: Int): Int {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, listSong[position].uri)
        val duration: String =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: "0"
        return duration.toInt()
    }
}