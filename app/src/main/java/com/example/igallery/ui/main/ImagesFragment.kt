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
import com.example.igallery.ui.Baseragment
import com.example.igallery.ui.adapter.ImageAdapter
import com.example.igallery.ui.fullscreen.FullScreenActivity
import com.example.igallery.util.CustomViewModelFactory
import com.example.igallery.util.GridSpacingItemDecoration
import com.example.igallery.util.PaginationScrollListener

class ImagesFragment : Baseragment<FragmentMediaBinding>() {

    private lateinit var imageAdapter: ImageAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        val db = GalleryDatabase.getDataBase(requireContext())
        val repository = Repository(db, requireActivity().contentResolver)
        CustomViewModelFactory(repository)
    }

    private fun setupObservers() {
        mainViewModel.progress.observe(this) {
            Log.d(TAG, "progress: ${it}")
        }
        mainViewModel.images.observe(this) {
            it?.let {
                imageAdapter.setImages(it)
                Log.d(TAG, "storagePermissionGranted: ${it.size}")
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMediaBinding
        get() = FragmentMediaBinding::inflate

    companion object {
        private const val TAG = "PhotosFragment"
    }

    override fun setup() {
        imageAdapter = ImageAdapter {
            FullScreenActivity.start(requireContext(), it.path)
        }
        binding.rvMedia.apply {
            val gridLayoutManager = GridLayoutManager(context, 4)
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(4, 8, true))
            adapter = imageAdapter
            addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
                override fun loadMoreItems() {
                    mainViewModel.loadMoreImages()
                }

                override fun isLoading() = false
            })
        }
        setupObservers()
        mainViewModel.loadInitialImages()
    }
}