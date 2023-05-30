package com.example.igallery.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.igallery.databinding.ActivitySearchBinding
import com.example.igallery.ui.adapter.ImageAdapter
import com.example.igallery.ui.base.BaseActivity
import com.example.igallery.ui.fullscreen.FullScreenActivity
import com.example.igallery.ui.main.ImagesFragment
import com.example.igallery.util.GridSpacingItemDecoration
import com.example.igallery.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var imageAdapter: ImageAdapter

    override fun setup() {
        requestStoragePermission()
    }

    override fun storagePermissionGranted() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.setQuery("${text ?: ""}".trim())
        }
        viewModel.setQuery("")
        setupRecyclerView()
        setupObservers()
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate


    companion object {
        private const val TAG = "SearchActivity"
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter(callback = {
            FullScreenActivity.start(this, it.path)
        }, search = true)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvSearch.apply {
            layoutManager = linearLayoutManager
            adapter = imageAdapter
            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.loadMoreImages()
                }

                override fun isLoading() = false
            })
        }
    }

    private fun setupObservers() {
        viewModel.getProgress().observe(this) {
            Log.d(TAG, "progress: ${it}")
        }
        viewModel.getQuery().observe(this) {
            viewModel.loadInitialImages()
        }
        viewModel.getImages().observe(this) {
            it?.let {
                imageAdapter.setImages(it)
                Log.d(TAG, "storagePermissionGranted: ${it.size}")
            }
        }
    }


}