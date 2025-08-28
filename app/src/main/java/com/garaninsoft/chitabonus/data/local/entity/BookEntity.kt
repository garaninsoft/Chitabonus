package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String? = null,

    @ColumnInfo(name = "pages")
    val pages: Int? = null,

    @ColumnInfo(name = "cover_uri")
    val coverUri: String? = null,

    @ColumnInfo(name = "age_from")
    val ageFrom: Int? = null,

    @ColumnInfo(name = "age_to")
    val ageTo: Int? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis() // Значение по умолчанию - текущее время
)
