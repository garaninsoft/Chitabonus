package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.AnswerEntity
import com.garaninsoft.chitabonus.domain.model.Answer
import java.sql.Timestamp

fun Answer.toEntity(): AnswerEntity {
    return AnswerEntity(
        id = this.id,
        questionId = this.questionId,
        childBookId = this.childBookId,
        childAnswer = this.childAnswer,
        isCorrect = this.isCorrect,
        attempts = this.attempts,
        timestamp = this.timestamp.time
    )
}

fun AnswerEntity.toDomain(): Answer {
    return Answer(
        id = this.id,
        questionId = this.questionId,
        childBookId = this.childBookId,
        childAnswer = this.childAnswer,
        isCorrect = this.isCorrect,
        attempts = this.attempts,
        timestamp = Timestamp(this.timestamp)
    )
}