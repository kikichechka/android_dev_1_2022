package com.example.photosights.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photosights.databinding.ItemPhotoBinding
import com.example.photosights.model.Photo

class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.imageUri == newItem.imageUri
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

class ListPhotoAdapter(
    private val onLongClick: (String, View) -> Unit,
    private val onClick: (Photo) -> Unit
) : ListAdapter<Photo, ListPhotoViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPhotoViewHolder {
        return ListPhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListPhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            Glide.with(photoContainer.context)
                .load(item.imageUri)
                .into(photoContainer)
        }

        holder.binding.root.apply {
            this.setOnLongClickListener {
                onLongClick(item.imageUri, holder.binding.pointForMenu)
                true
            }
            this.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class ListPhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
