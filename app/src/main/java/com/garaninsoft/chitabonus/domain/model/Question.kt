package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

data class Question(
    val id: Long = 0,
    val bookId: Long,
    val text: String,
    val correctAnswer: String,
    val isMain: Boolean,
    val difficulty: Int,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
)
