package com.garaninsoft.chitabonus.data.di

import android.content.Context
import androidx.room.Room
import com.garaninsoft.chitabonus.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chitabonus-db" // Имя файла БД
        ).build()
    }

    // Предоставляем все Dao
    @Provides
    fun provideChildDao(appDatabase: AppDatabase) = appDatabase.childDao()

    @Provides
    fun provideBookDao(appDatabase: AppDatabase) = appDatabase.bookDao()

    @Provides
    fun provideChildBookDao(appDatabase: AppDatabase) = appDatabase.childBookDao()

    @Provides
    fun provideQuestionDao(appDatabase: AppDatabase) = appDatabase.questionDao()

    @Provides
    fun provideAnswerDao(appDatabase: AppDatabase) = appDatabase.answerDao()

    @Provides
    fun provideSpendingDao(appDatabase: AppDatabase) = appDatabase.spendingDao()

    @Provides
    fun provideSettingsDao(appDatabase: AppDatabase) = appDatabase.settingsDao()


}