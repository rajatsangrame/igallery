package com.example.igallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Image
import com.example.igallery.databinding.ItemImageBinding
import com.example.igallery.databinding.ItemSearchImageBinding
import com.example.igallery.util.loadThumbnail

class ImageAdapter(
    private var images: MutableList<Image> = ArrayList(),
    private val callback: (Image) -> Unit,
    private val search: Boolean = false
) :
    RecyclerView.Adapter<BaseViewHolder<Image>>() {

    fun setImages(newImages: List<Image>) {
        val diffCallback = DiffUtilCallBack(images, newImages)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        images.clear()
        images.addAll(newImages)
        diffCourses.dispatchUpdatesTo(this)
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

    inner class DiffUtilCallBack(
        private val oldList: List<Image>,
        private val newList: List<Image>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when {
                oldList[oldItemPosition].id != newList[newItemPosition].id -> false
                oldList[oldItemPosition].path != newList[newItemPosition].path -> false
                oldList[oldItemPosition].name != newList[newItemPosition].name -> false
                else -> true
            }
        }
    }

}
