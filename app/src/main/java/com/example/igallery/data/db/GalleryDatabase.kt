package com.example.igallery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Folder::class, Image::class], version = 1)
@TypeConverters(DbTypeConverter::class)
abstract class GalleryDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao

    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: GalleryDatabase? = null

        fun getDataBase(context: Context): GalleryDatabase {
            if (INSTANCE == null) {
                synchronized(GalleryDatabase::class) {
                    if (INSTANCE == null)
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GalleryDatabase::class.java, "database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}
