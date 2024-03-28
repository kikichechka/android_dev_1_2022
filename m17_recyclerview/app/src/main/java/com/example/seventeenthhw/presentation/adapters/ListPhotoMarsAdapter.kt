package com.example.seventeenthhw.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seventeenthhw.data.model.Mars
import com.example.seventeenthhw.databinding.ItemPhotoMarsBinding

class ListPhotoMarsAdapter(private val onClick: (Mars) -> Unit) :
    RecyclerView.Adapter<ListPhotoMarsViewHolder>() {
    private var list: List<Mars> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<Mars>) {
        this.list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPhotoMarsViewHolder {
        val binding = ItemPhotoMarsBinding.inflate(LayoutInflater.from(parent.context))
        return ListPhotoMarsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPhotoMarsViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            rover.text = "Rover: ${item.rover.name}"
            camera.text = "Camera: ${item.camera.name}"
            sol.text = "Sol: ${item.sol}"
            date.text = "Date: ${item.earthDate}"
            item.let {
                Glide
                    .with(image.context)
                    .load(it.imgSrc)
                    .centerCrop()
                    .into(image)
            }
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class ListPhotoMarsViewHolder(val binding: ItemPhotoMarsBinding) :
    RecyclerView.ViewHolder(binding.root)
