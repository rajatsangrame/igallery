package com.example.igallery.data

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import com.example.igallery.data.db.Folder
import com.example.igallery.data.db.GalleryDatabase
import com.example.igallery.data.db.Image

class Repository(private val db: GalleryDatabase, private val contentResolver: ContentResolver) {

    fun getFolders() = db.folderDao().getAll()
    fun getImages() = db.imageDao().getAll()

    suspend fun loadImages() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID
        )
        val sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
        if (cursor == null || !cursor.moveToFirst()) return
        val imageList = mutableListOf<Image>()
        val folders = mutableSetOf<Folder>()
        do {
            val imageId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
            val folderName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
            val folderId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID))

            val image = Image(imageId, folderId, name, date, path)
            imageList.add(image)
            val folder = Folder(folderId, folderName)
            folders.add(folder)
        } while (cursor.moveToNext())
        cursor.close()
        db.imageDao().bulkInsert(imageList)
        db.folderDao().bulkInsert(folders.toList())
    }

}