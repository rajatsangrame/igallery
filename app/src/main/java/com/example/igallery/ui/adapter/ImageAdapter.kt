package com.example.igallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Image
import com.example.igallery.databinding.ItemImageBinding
import com.example.igallery.databinding.ItemSearchImageBinding
import com.example.igallery.util.loadThumbnail

class ImageAdapter(
    private var images: List<Image> = ArrayList(),
    private val callback: (Image) -> Unit,
    private val search: Boolean = false
) :
    RecyclerView.Adapter<BaseViewHolder<Image>>() {

    fun setImages(images: List<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Image> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (search) {
            val binding = ItemSearchImageBinding.inflate(layoutInflater, parent, false)
            SearchViewHolder(binding)
        } else {
            val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Image>, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(private val binding: ItemImageBinding) : BaseViewHolder<Image>(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(images[adapterPosition])
            }
        }

        override fun bind(data: Image) {
            binding.image.loadThumbnail(data.path)
        }

        override fun bind(position: Int) {
        }
    }

    inner class SearchViewHolder(private val binding: ItemSearchImageBinding) : BaseViewHolder<Image>(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(images[adapterPosition])
            }
        }

        override fun bind(data: Image) {
            binding.text.text = data.name
            binding.image.loadThumbnail(data.path, true)
        }

        override fun bind(position: Int) {
        }
    }


}
