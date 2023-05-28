package com.example.igallery.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igallery.data.Repository
import com.example.igallery.data.db.Folder
import com.example.igallery.data.db.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val progress = MutableLiveData(false)
    fun getFolders() = repository.getFolders()
    fun getImages() = repository.getImages()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            repository.loadImages()
            progress.postValue(false)
        }
    }
}
