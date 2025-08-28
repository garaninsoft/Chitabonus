package com.garaninsoft.chitabonus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.garaninsoft.chitabonus.data.local.dao.AnswerDao
import com.garaninsoft.chitabonus.data.local.dao.BookDao
import com.garaninsoft.chitabonus.data.local.dao.ChildBookDao
import com.garaninsoft.chitabonus.data.local.dao.ChildDao
import com.garaninsoft.chitabonus.data.local.dao.QuestionDao
import com.garaninsoft.chitabonus.data.local.dao.SettingsDao
import com.garaninsoft.chitabonus.data.local.dao.SpendingDao
import com.garaninsoft.chitabonus.data.local.entity.AnswerEntity

import com.garaninsoft.chitabonus.data.local.entity.BookEntity
import com.garaninsoft.chitabonus.data.local.entity.ChildBookEntity
import com.garaninsoft.chitabonus.data.local.entity.ChildEntity
import com.garaninsoft.chitabonus.data.local.entity.QuestionEntity
import com.garaninsoft.chitabonus.data.local.entity.SpendingEntity
import com.garaninsoft.chitabonus.data.local.entity.SettingEntity

@Database(
    entities = [
        ChildEntity::class,
        BookEntity::class,
        ChildBookEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
        SpendingEntity::class,
        SettingEntity::class
    ],
    version = 1, // Начинаем с версии 1
    exportSchema = true // Полезно для миграций
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun bookDao(): BookDao
    abstract fun childBookDao(): ChildBookDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun spendingDao(): SpendingDao
    abstract fun settingsDao(): SettingsDao
}