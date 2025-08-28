package com.garaninsoft.chitabonus.data.di

import com.garaninsoft.chitabonus.data.initial.InitialDataManager
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InitializationModule {

    @Provides
    @Singleton
    fun provideInitialDataManager(
        settingsRepository: SettingsRepository,
        bookRepository: BookRepository
    ): InitialDataManager {
        return InitialDataManager(settingsRepository, bookRepository)
    }
}