package com.example.igallery.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.igallery.R
import com.example.igallery.data.Repository
import com.example.igallery.data.db.GalleryDatabase
import com.example.igallery.databinding.FragmentMediaBinding
import com.example.igallery.ui.*
import com.example.igallery.ui.adapter.FolderAdapter
import com.example.igallery.util.CustomViewModelFactory
import com.example.igallery.util.GridSpacingItemDecoration

class FoldersFragment : Baseragment<FragmentMediaBinding>() {

    private lateinit var folderAdapter: FolderAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        val db = GalleryDatabase.getDataBase(requireContext())
        val repository = Repository(db, requireActivity().contentResolver)
        CustomViewModelFactory(repository)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMediaBinding
        get() = FragmentMediaBinding::inflate

    override fun setup() {
        folderAdapter = FolderAdapter {
            mainViewModel.folderId = it.id
            findNavController().navigate(R.id.action_FoldersFragment_to_ImagesFragment)
        }
        binding.rvMedia.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(3, 32, true))
            adapter = folderAdapter
        }
        setupObservers()
        mainViewModel.loadInitialFolders()
    }

    private fun setupObservers() {
        mainViewModel.progress.observe(this) {
            Log.d(TAG, "progress: ${it}")
        }
        mainViewModel.folders.observe(this) {
            it?.let {
                folderAdapter.setFolders(it)
                Log.d(TAG, "storagePermissionGranted: ${it.size}")
            }
        }
    }

    companion object {
        private const val TAG = "FoldersFragment"
    }
}