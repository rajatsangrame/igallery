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
import com.example.igallery.ui.Baseragment
import com.example.igallery.ui.adapter.ImageAdapter
import com.example.igallery.ui.fullscreen.FullScreenActivity
import com.example.igallery.util.GridSpacingItemDecoration
import com.example.igallery.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : Baseragment<FragmentMediaBinding>() {

    companion object {
        private const val TAG = "PhotosFragment"
    }

    private lateinit var imageAdapter: ImageAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    private fun setupObservers() {
        mainViewModel.getProgress().observe(this) {
            Log.d(TAG, "progress: ${it}")
        }
        mainViewModel.getImages().observe(this) {
            it?.let {
                imageAdapter.setImages(it)
                Log.d(TAG, "storagePermissionGranted: ${it.size}")
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMediaBinding
        get() = FragmentMediaBinding::inflate

    private fun setupToolbar() {
        binding.toolbar.title.text = mainViewModel.selectedFolder?.name ?: getString(R.string.images)
        binding.toolbar.search.visibility = View.GONE
        binding.toolbar.back.visibility = View.VISIBLE
    }

    override fun setup() {
        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()
        mainViewModel.loadInitialImages()
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter(callback = {
            FullScreenActivity.start(requireContext(), it.path)
        })
        binding.rvMedia.apply {
            val gridLayoutManager = GridLayoutManager(context, 4)
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(4, 1, false))
            adapter = imageAdapter
            addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
                override fun loadMoreItems() {
                    mainViewModel.loadMoreImages()
                }

                override fun isLoading() = false
            })
        }
    }

    private fun setupListeners() {
        binding.toolbar.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}