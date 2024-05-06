package com.example.seventeenthhw.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seventeenthhw.data.model.AstronomyPicture
import com.example.seventeenthhw.databinding.ItemAstronomyPictureBinding

class ListPhotoAstronomyPictureAdapter(private val onClick: (AstronomyPicture) -> Unit) :
    ListAdapter<AstronomyPicture, ListPhotoAstronomyPictureViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPhotoAstronomyPictureViewHolder {
        val binding = ItemAstronomyPictureBinding.inflate(LayoutInflater.from(parent.context))
        return ListPhotoAstronomyPictureViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPhotoAstronomyPictureViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = "Title: ${item.title}"
            date.text = "Date: ${item.date}"
            item.let {
                Glide
                    .with(image.context)
                    .load(it.url)
                    .centerCrop()
                    .into(image)
            }
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    companion object {
        private object DiffUtilCallback : DiffUtil.ItemCallback<AstronomyPicture>() {
            override fun areItemsTheSame(
                oldItem: AstronomyPicture,
                newItem: AstronomyPicture
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: AstronomyPicture,
                newItem: AstronomyPicture
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ListPhotoAstronomyPictureViewHolder(val binding: ItemAstronomyPictureBinding) :
    RecyclerView.ViewHolder(binding.root)
