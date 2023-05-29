package com.example.igallery.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.igallery.data.Repository
import com.example.igallery.ui.main.MainViewModel

class CustomViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        when (modelClass) {
            MainViewModel::class.java -> return MainViewModel(repository) as T
            else -> throw java.lang.RuntimeException("Not supported class ${modelClass.simpleName}")
        }

    }
}
