package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

// Имя таблицы можно задать явно
@Entity(tableName = "children")
data class ChildEntity(
    // autoGenerate = true - база данных сама присвоит новый ID
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    // В БД часто хранят дату в виде временной метки (timestamp)
    @ColumnInfo(name = "birth_date")
    val birthDate: Long,

    @ColumnInfo(name = "avatar_uri")
    val avatarUri: String?,

    // ColumnInfo можно опустить, если имя поля совпадает с именем столбца
    @ColumnInfo(name = "current_balance")
    val currentBalance: Double // Real в SQLite соответствует Double в Kotlin
)