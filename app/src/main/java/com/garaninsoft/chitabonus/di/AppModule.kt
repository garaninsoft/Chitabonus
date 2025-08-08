package com.garaninsoft.chitabonus.di

import android.content.Context
import com.garaninsoft.chitabonus.data.BookRepository
import com.garaninsoft.chitabonus.data.storage.BookStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookStorage(@ApplicationContext appContext: Context): BookStorage = BookStorage(appContext)

    @Provides
    @Singleton
    fun provideBookRepository(storage: BookStorage): BookRepository = BookRepository(storage)
}