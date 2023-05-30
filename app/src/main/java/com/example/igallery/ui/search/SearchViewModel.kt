package com.example.igallery.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igallery.data.Repository
import com.example.igallery.data.db.Folder
import com.example.igallery.data.db.Image
import com.example.igallery.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val images = MutableLiveData<MutableList<Image>>()
    fun getImages(): LiveData<MutableList<Image>> = images

    private val query = MutableLiveData<String>()
    private var searchJob: Job? = null
    fun setQuery(q: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            query.value = q
        }
    }

    fun getQuery(): LiveData<String> = query

    private var imageOffset = 0

    private suspend fun searchImages(query: String, offset: Int, size: Int = 10): MutableList<Image> {
        return repository.searchImages(query = query, offset = offset, size = size)
    }

    fun loadInitialImages() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            imageOffset = 0
            val data = searchImages(query = query.value ?: "", offset = imageOffset)
            images.postValue(data)
            progress.postValue(false)
        }
    }

    fun loadMoreImages() {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(true)
            imageOffset += 10
            val data = searchImages(query = query.value ?: "", offset = imageOffset)
            val old = images.value ?: mutableListOf()
            old.addAll(data)
            images.postValue(old)
            progress.postValue(false)
        }
    }

}
