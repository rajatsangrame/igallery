package com.example.igallery.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.igallery.R
import com.example.igallery.databinding.AcitivityMainBinding
import com.example.igallery.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<AcitivityMainBinding>() {

    override fun setup() {
        requestStoragePermission()
    }

    override fun storagePermissionGranted() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        navHostFragment.navController.graph = graph
    }

    override val bindingInflater: (LayoutInflater) -> AcitivityMainBinding
        get() = AcitivityMainBinding::inflate


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}