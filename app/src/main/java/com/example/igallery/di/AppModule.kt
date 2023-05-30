package com.example.igallery.di

import android.content.Context
import com.example.igallery.data.DataSource
import com.example.igallery.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(@ApplicationContext context: Context, dataSource: DataSource): Repository {
        return Repository(dataSource.buildGalleryDatabase(context), dataSource.buildContentResolver(context))
    }

}
