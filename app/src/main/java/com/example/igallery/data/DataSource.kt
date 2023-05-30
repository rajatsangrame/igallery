package com.example.igallery.data

import android.content.ContentResolver
import android.content.Context
import com.example.igallery.data.db.GalleryDatabase
import javax.inject.Inject

class DataSource @Inject constructor() {

    fun buildGalleryDatabase(context: Context): GalleryDatabase {
        return GalleryDatabase.getDataBase(context)
    }

    fun buildContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }
}