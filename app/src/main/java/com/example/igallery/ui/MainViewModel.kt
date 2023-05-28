package com.example.igallery.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igallery.data.Repository
import com.example.igallery.data.db.Folder
import com.example.igallery.data.db.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val images = MutableLiveData<MutableList<Image>>()
    val folders = MutableLiveData<MutableList<Folder>>()
    val progress = MutableLiveData(false)

    private var folderOffset = 0
    private var imageOffset = 0
    private var dataLoaded = false

    // region Images

    private suspend fun getImages(offset: Int, size: Int = 10): MutableList<Image> {
        return repository.getImages(offset = offset, size = size)
    }

    fun loadInitialImages() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            if (!dataLoaded) loadData().join()

            imageOffset = 0
            val data = getImages(offset = imageOffset)
            images.postValue(data)
            progress.postValue(false)
        }
    }

    fun loadMoreImages() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            imageOffset += 10
            val data = getImages(offset = imageOffset)
            val old = images.value ?: mutableListOf()
            old.addAll(data)
            images.postValue(old)
            progress.postValue(false)
        }
    }

    // endregion

    // region Folders

    private suspend fun getFolders(offset: Int, size: Int = 10): MutableList<Folder> {
        return repository.getFolders(offset = offset, size = size)
    }

    fun loadInitialFolders() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            if (!dataLoaded) loadData().join()

            folderOffset = 0
            val data = getFolders(offset = folderOffset)
            folders.postValue(data)
            progress.postValue(false)
        }
    }

    fun loadMoreFolder() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            folderOffset += 10
            val data = getFolders(offset = folderOffset)
            val old = folders.value ?: mutableListOf()
            old.addAll(data)
            folders.postValue(old)
            progress.postValue(false)
        }
    }

    private fun loadData(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            repository.loadImages()
            dataLoaded = true
        }
    }

    // endregion

}
