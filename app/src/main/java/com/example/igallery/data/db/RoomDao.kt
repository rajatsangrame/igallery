package com.example.igallery.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(folders: MutableList<Folder>)

    @Query("SELECT * FROM folder LIMIT :batchSize OFFSET :offset")
    suspend fun get(offset: Int, batchSize: Int): MutableList<Folder>

}

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(images: MutableList<Image>)

    @Query("SELECT * FROM image LIMIT :batchSize OFFSET :offset")
    suspend fun get(offset: Int, batchSize: Int): MutableList<Image>

}