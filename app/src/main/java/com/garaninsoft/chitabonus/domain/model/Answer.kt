package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

data class Answer(
    val id: Long,
    val questionId: Long,
    val childBookId: Long,
    val childAnswer: String?,
    val isCorrect: Boolean?,
    val attempts: Int,
    val timestamp: Timestamp
)
