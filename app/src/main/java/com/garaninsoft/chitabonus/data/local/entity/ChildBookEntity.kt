package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

// Определяем внешние ключи для целостности данных
@Entity(
    tableName = "child_books",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = ["id"],
            childColumns = ["child_id"],
            onDelete = ForeignKey.CASCADE // Если удаляют ребёнка, все его книги тоже удаляются
        ),
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE // Если удаляют книгу, все её связи с детьми удаляются
        )
    ]
)
data class ChildBookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "child_id", index = true) // Индекс для ускорения запросов
    val childId: Long,

    @ColumnInfo(name = "book_id", index = true) // Индекс для ускорения запросов
    val bookId: Long,

    // Храним статус как Int в БД, а в коде используем enum
    @ColumnInfo(name = "status")
    val status: Int,

    @ColumnInfo(name = "assigned_at")
    val assignedAt: Long,

    @ColumnInfo(name = "completed_at")
    val completedAt: Long? = null // null, если книга ещё не завершена
)