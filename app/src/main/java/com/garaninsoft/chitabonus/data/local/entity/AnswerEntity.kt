package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChildBookEntity::class,
            parentColumns = ["id"],
            childColumns = ["child_book_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "question_id", index = true)
    val questionId: Long,

    @ColumnInfo(name = "child_book_id", index = true)
    val childBookId: Long,

    @ColumnInfo(name = "child_answer")
    val childAnswer: String? = null,

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean? = null,

    @ColumnInfo(name = "attempts")
    val attempts: Int, // Номер попытки, в которой был дан ответ

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)