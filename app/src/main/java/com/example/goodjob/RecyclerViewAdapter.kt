package com.example.goodjob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private var item: Array<EmojiDBHelper.Emoji>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val drawableID =
        arrayOf(
            R.drawable.emoji_angry, R.drawable.emoji_basic, R.drawable.emoji_best,
            R.drawable.emoji_dejected, R.drawable.emoji_depression, R.drawable.emoji_happy,
            R.drawable.emoji_proud, R.drawable.emoji_sad, R.drawable.emoji_sick
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_emotion_statistics_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = getNameToDrawableID(item[position])
        val count = item[position].emojiCount
        val str = "${count}개"
        holder.rvItemIv.setImageResource(id)
        holder.rvItemPb.progress = count
        holder.rvItemTv.text = str
    }

    // EmojiName 과 Drawable ID 비교
    private fun getNameToDrawableID(emoji: EmojiDBHelper.Emoji): Int {
        var id: Int = -1
        when (emoji.emojiName) {
            "mood_angry" -> id = drawableID[0]
            "mood_basic" -> id = drawableID[1]
            "mood_best" -> id = drawableID[2]
            "mood_dejected" -> id = drawableID[3]
            "mood_depression" -> id = drawableID[4]
            "mood_happy" -> id = drawableID[5]
            "mood_proud" -> id = drawableID[6]
            "mood_sad" -> id = drawableID[7]
            "mood_sick" -> id = drawableID[8]
        }
        return if (id != -1)
            id
        else
            -1
    }

    // ViewHolder Class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvItemIv: ImageView =
            itemView.findViewById(R.id.activity_emotion_statistics_ivRVItem)
        val rvItemPb: ProgressBar =
            itemView.findViewById(R.id.activity_emotion_statistics_pbRVItem)
        val rvItemTv: TextView =
            itemView.findViewById(R.id.activity_emotion_statistics_tvRVItem)
    }
}