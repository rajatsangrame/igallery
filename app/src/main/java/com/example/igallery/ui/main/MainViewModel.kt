package com.example.igallery.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.igallery.data.Repository
import com.example.igallery.data.db.Folder
import com.example.igallery.data.db.Image
import com.example.igallery.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val images = MutableLiveData<MutableList<Image>?>()
    fun getImages(): LiveData<MutableList<Image>?> = images

    private val folders = MutableLiveData<MutableList<Folder>>()
    fun getFolders(): LiveData<MutableList<Folder>> = folders

    // User selected folder id to be used to fetch images for selected folder
    var selectedFolder: Folder? = null

    private var folderOffset = 0
    private var imageOffset = 0

    // region Images

    private suspend fun getImages(folderId: String, offset: Int, size: Int = 10): MutableList<Image> {
        return repository.getImages(folderId = folderId, offset = offset, size = size)
    }

    fun loadInitialImages() {
        if (selectedFolder?.id == null) return
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            loadData().join()

            imageOffset = 0
            val data = getImages(folderId = selectedFolder!!.id, offset = imageOffset)
            images.postValue(data)
            progress.postValue(false)
        }
    }

    fun loadMoreImages() {
        if (selectedFolder?.id == null) return
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            imageOffset += 10
            val data = getImages(folderId = selectedFolder!!.id, offset = imageOffset)
            val old = images.value ?: mutableListOf()
            old.addAll(data)
            images.postValue(old)
            progress.postValue(false)
        }
    }

    fun resetImageData() {
        selectedFolder = null
        images.value = null
    }

    // endregion

    // region Folders

    private suspend fun getFolders(offset: Int, size: Int = 10): MutableList<Folder> {
        return repository.getFolders(offset = offset, size = size)
    }

    fun loadInitialFolders() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            loadData().join()

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
        }
    }

    // endregion

}
