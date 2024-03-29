package com.example.igallery.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.igallery.R
import com.example.igallery.databinding.FragmentMediaBinding
import com.example.igallery.ui.*
import com.example.igallery.ui.adapter.FolderAdapter
import com.example.igallery.ui.search.SearchActivity
import com.example.igallery.util.GridSpacingItemDecoration
import com.example.igallery.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoldersFragment : Baseragment<FragmentMediaBinding>() {

    private lateinit var folderAdapter: FolderAdapter

    private val mainViewModel: MainViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMediaBinding
        get() = FragmentMediaBinding::inflate

    override fun setup() {
        mainViewModel.resetImageData()
        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()
        mainViewModel.loadInitialFolders()
    }

    private fun setupRecyclerView() {
        folderAdapter = FolderAdapter {
            mainViewModel.selectedFolder = it
            findNavController().navigate(R.id.action_FoldersFragment_to_ImagesFragment)
        }
        binding.rvMedia.apply {
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(3, 32, true))
            adapter = folderAdapter
            addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
                override fun loadMoreItems() {
                    mainViewModel.loadMoreFolder()
                }

                override fun isLoading() = false
            })
        }
    }

    private fun setupToolbar() {
        binding.toolbar.title.text = getString(R.string.folder)
        binding.toolbar.search.visibility = View.VISIBLE
    }

    private fun setupListeners() {
        binding.toolbar.search.setOnClickListener {
            SearchActivity.start(requireContext())
        }
    }

    private fun setupObservers() {
        mainViewModel.getProgress().observe(this) {
            Log.d(TAG, "progress: ${it}")
        }
        mainViewModel.getFolders().observe(this) {
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