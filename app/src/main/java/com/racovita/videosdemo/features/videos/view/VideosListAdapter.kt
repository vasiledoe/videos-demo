package com.racovita.videosdemo.features.videos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.racovita.videosdemo.R
import com.racovita.videosdemo.data.models.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_video_item.view.*

class VideosListAdapter(
    private var items: MutableList<Video>,
    private val onClickItemListener: (Video) -> Unit
) : RecyclerView.Adapter<VideosListAdapter.ViewHolder>() {


    /**
     * add items from pagination - so use a smart notify for the new range only
     */
    fun addItems(newItems: List<Video>) {
        val listSize = items.size
        items.addAll(newItems)
        notifyItemRangeChanged(listSize, items.size - 1)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_video_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fillUpItem(items[position], onClickItemListener)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val wholeZone: MaterialCardView = view.whole_item
        private val ivThumb: ImageView = view.iv_thumb
        private val tvTitle: TextView = view.tv_title
        private val tvRating: TextView = view.tv_rating
        private val tvRaters: TextView = view.tv_raters
        private val tvDescription: TextView = view.tv_description
        private val ratingBar: RatingBar = view.rating

        fun fillUpItem(apiVideo: Video, listener: (Video) -> Unit) {
            tvTitle.text = apiVideo.title
            tvRating.text = apiVideo.ratingStr
            tvRaters.text = apiVideo.raters
            tvDescription.text = apiVideo.overview
            ratingBar.rating = apiVideo.rating

            Picasso.get().load(apiVideo.thumb).error(R.color.divider).into(ivThumb)

            wholeZone.setOnClickListener { listener(apiVideo) }
        }
    }
}