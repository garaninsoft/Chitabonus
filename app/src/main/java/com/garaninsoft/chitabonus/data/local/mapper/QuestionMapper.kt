package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.QuestionEntity
import com.garaninsoft.chitabonus.domain.model.Question
import java.sql.Timestamp

fun Question.toEntity(): QuestionEntity {
    return QuestionEntity(
        id = this.id,
        bookId = this.bookId,
        text = this.text,
        correctAnswer = this.correctAnswer,
        isMain = this.isMain,
        difficulty = this.difficulty,
        timestamp = this.timestamp.time
    )
}

fun QuestionEntity.toDomain(): Question {
    return Question(
        id = this.id,
        bookId = this.bookId,
        text = this.text,
        correctAnswer = this.correctAnswer,
        isMain = this.isMain,
        difficulty = this.difficulty,
        timestamp = Timestamp(this.timestamp)
    )
}