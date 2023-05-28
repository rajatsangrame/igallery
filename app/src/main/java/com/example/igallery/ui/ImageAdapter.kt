package com.example.igallery.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Image
import com.example.igallery.databinding.ItemFolderBinding
import com.example.igallery.util.loadThumbnail

class ImageAdapter(
    private var images: MutableList<Image> = ArrayList()
) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    fun setImages(images: List<Image>) {
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFolderBinding.inflate(
            layoutInflater,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(private val binding: ItemFolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.text.text = image.name
            binding.image.loadThumbnail(Uri.parse(image.path))
        }
    }

}
