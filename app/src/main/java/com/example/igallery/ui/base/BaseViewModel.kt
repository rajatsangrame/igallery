package com.example.igallery.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val progress = MutableLiveData(false)
    fun getProgress(): LiveData<Boolean> = progress
}
