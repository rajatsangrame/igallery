package com.example.igallery.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.igallery.R
import com.example.igallery.data.Repository
import com.example.igallery.data.db.GalleryDatabase
import com.example.igallery.databinding.ActivityFoldersBinding
import com.example.igallery.util.CustomViewModelFactory
import com.example.igallery.util.GridSpacingItemDecoration

class FoldersActivity : AppCompatActivity() {

    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) storagePermissionGranted()
        else Log.d(TAG, "storage permission not granted")
    }
    private lateinit var binding: ActivityFoldersBinding

    private lateinit var folderAdapter: FolderAdapter


    private val mainViewModel: MainViewModel by viewModels {
        val db = GalleryDatabase.getDataBase(this)
        val repository = Repository(db, contentResolver)
        CustomViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoldersBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        requestStoragePermission()
        setupObservers()
    }

    private fun setupUi() {
        folderAdapter = FolderAdapter()
        binding.rvFolders.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(3, 32, true))
            adapter = folderAdapter
        }

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

    private fun requestStoragePermission() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED -> {
                permissionResult.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED -> {
                permissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> storagePermissionGranted()
        }
    }

    private fun storagePermissionGranted() {
        setupUi()
    }

    fun more(view: View) {
        mainViewModel.loadMoreImages()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}