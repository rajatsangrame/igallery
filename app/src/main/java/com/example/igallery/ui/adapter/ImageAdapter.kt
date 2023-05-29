package com.example.igallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Image
import com.example.igallery.databinding.ItemMediaBinding
import com.example.igallery.util.loadThumbnail

class ImageAdapter(
    private var images: List<Image> = ArrayList(),
    private val callback: (Image) -> Unit
) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    fun setImages(images: List<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMediaBinding.inflate(
            layoutInflater,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(private val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(images[adapterPosition])
            }
        }

        fun bind(image: Image) {
            binding.text.text = image.name
            binding.image.loadThumbnail(image.path)
        }
    }

}
