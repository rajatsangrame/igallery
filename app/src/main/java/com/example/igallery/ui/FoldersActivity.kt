package com.example.igallery.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.igallery.R
import com.example.igallery.data.Repository
import com.example.igallery.data.db.GalleryDatabase
import com.example.igallery.util.CustomViewModelFactory

class FoldersActivity : AppCompatActivity() {

    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) storagePermissionGranted()
        else Log.d(TAG, "storage permission not granted")
    }

    private val mainViewModel: MainViewModel by viewModels {
        val db = GalleryDatabase.getDataBase(this)
        val repository = Repository(db, contentResolver)
        CustomViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestStoragePermission()

        mainViewModel.progress.observe(this, {})
        mainViewModel.getFolders().observe(this) {
            it?.let {
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
        mainViewModel.loadData()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}