package com.example.igallery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Folder
import com.example.igallery.databinding.ItemMediaBinding
import com.example.igallery.util.loadThumbnail

class FolderAdapter(
    private var folders: List<Folder> = ArrayList(),
    private val callback: (Folder) -> Unit
) :
    RecyclerView.Adapter<FolderAdapter.ViewHolder>() {

    fun setFolders(folders: List<Folder>) {
        this.folders = folders
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
        holder.bind(folders[position])
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    inner class ViewHolder(private val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback(folders[adapterPosition])
            }
        }

        fun bind(folder: Folder) {
            binding.text.text = folder.name
            binding.image.loadThumbnail(folder.path, true)
        }
    }

}
