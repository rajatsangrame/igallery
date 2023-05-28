package com.example.igallery.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.igallery.data.db.Folder
import com.example.igallery.databinding.ItemFolderBinding
import com.example.igallery.util.loadThumbnail

class FolderAdapter(
    private var folders: MutableList<Folder> = ArrayList()
) :
    RecyclerView.Adapter<FolderAdapter.ViewHolder>() {

    fun setFolders(folders: List<Folder>) {
        this.folders.addAll(folders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFolderBinding.inflate(
            layoutInflater,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderAdapter.ViewHolder, position: Int) {
        holder.bind(folders[position])
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    inner class ViewHolder(private val binding: ItemFolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(folder: Folder) {
            binding.text.text = folder.name
            binding.image.loadThumbnail(folder.path)
        }
    }

}
