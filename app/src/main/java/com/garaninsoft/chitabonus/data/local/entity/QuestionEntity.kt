package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE // При удалении книги удаляются все её вопросы
        )
    ]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "book_id", index = true)
    val bookId: Long,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "correct_answer")
    val correctAnswer: String,

    @ColumnInfo(name = "is_main")
    val isMain: Boolean,

    @ColumnInfo(name = "difficulty")
    val difficulty: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)