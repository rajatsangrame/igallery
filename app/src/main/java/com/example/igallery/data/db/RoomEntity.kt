package com.example.igallery.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder")
data class Folder(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "path")
    val path: String
)


@Entity(tableName = "image")
data class Image(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "folderId")
    val folderId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "created")
    val created: String,

    @ColumnInfo(name = "path")
    val path: String
)
