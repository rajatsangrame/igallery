package com.example.igallery.data.db

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

    @Query("SELECT * FROM image WHERE folderId = :folderId LIMIT :batchSize OFFSET :offset")
    suspend fun get(folderId: String, offset: Int, batchSize: Int): MutableList<Image>

}