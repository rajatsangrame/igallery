package com.example.igallery.ui.fullscreen

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.WindowInsetsControllerCompat
import com.example.igallery.databinding.ActivityFullScreenBinding
import com.example.igallery.ui.base.BaseActivity
import java.io.File

class FullScreenActivity : BaseActivity<ActivityFullScreenBinding>() {

    override fun setup() {
        requestStoragePermission()
    }

    override fun storagePermissionGranted() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(systemBars())

        intent.getStringExtra(KEY_IMAGE_PATH)?.let {
            val file = File(it)
            binding.photoView.setImageURI(file.toUri())
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityFullScreenBinding
        get() = ActivityFullScreenBinding::inflate


    companion object {
        private const val KEY_IMAGE_PATH = "KEY_IMAGE_PATH"
        fun start(context: Context, imagePath: String) {
            val intent = Intent(context, FullScreenActivity::class.java)
            intent.putExtra(KEY_IMAGE_PATH, imagePath)
            context.startActivity(intent)
        }
    }
}