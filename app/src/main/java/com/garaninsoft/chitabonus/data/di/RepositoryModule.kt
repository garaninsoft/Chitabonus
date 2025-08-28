package com.garaninsoft.chitabonus.data.di

import com.garaninsoft.chitabonus.data.repository.AnswerRepositoryImpl
import com.garaninsoft.chitabonus.data.repository.BookRepositoryImpl
import com.garaninsoft.chitabonus.data.repository.ChildRepositoryImpl
import com.garaninsoft.chitabonus.data.repository.SettingsRepositoryImpl
import com.garaninsoft.chitabonus.domain.repository.SpendingRepository
import com.garaninsoft.chitabonus.data.repository.SpendingRepositoryImpl
import com.garaninsoft.chitabonus.domain.repository.AnswerRepository
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChildRepository(
        childRepositoryImpl: ChildRepositoryImpl
    ): ChildRepository

    @Binds
    @Singleton
    abstract fun bindAnswerRepository(
        answerRepositoryImpl: AnswerRepositoryImpl
    ): AnswerRepository

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindSpendingRepository(
        spendingRepositoryImpl: SpendingRepositoryImpl
    ): SpendingRepository
}