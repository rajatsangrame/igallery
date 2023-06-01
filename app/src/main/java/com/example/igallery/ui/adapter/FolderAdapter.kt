package com.example.igallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Folder
import com.example.igallery.databinding.ItemFolderBinding
import com.example.igallery.util.loadThumbnail

class FolderAdapter(
    private var folders: MutableList<Folder> = ArrayList(),
    private val callback: (Folder) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<Folder>>() {

    fun setFolders(newFolders: List<Folder>) {
        val diffCallback = DiffUtilCallBack(folders, newFolders)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        folders.clear()
        folders.addAll(newFolders)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Folder> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFolderBinding.inflate(
            layoutInflater,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Folder>, position: Int) {
        holder.bind(folders[position])
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    inner class ViewHolder(private val binding: ItemFolderBinding) : BaseViewHolder<Folder>(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(folders[adapterPosition])
            }
        }

        override fun bind(folder: Folder) {
            binding.text.text = folder.name
            binding.image.loadThumbnail(folder.path, true)
        }

        override fun bind(position: Int) {}
    }

    inner class DiffUtilCallBack(
        private val oldList: List<Folder>,
        private val newList: List<Folder>,
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
