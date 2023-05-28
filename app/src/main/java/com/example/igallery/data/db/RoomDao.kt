package com.example.igallery.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(folders: List<Folder>)

    @Query("SELECT * FROM folder")
    fun getAll(): LiveData<List<Folder>>

}

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(images: List<Image>)

    @Query("SELECT * FROM image")
    fun getAll(): LiveData<List<Image>>

    @Update()
    suspend fun update(folder: Image)

}