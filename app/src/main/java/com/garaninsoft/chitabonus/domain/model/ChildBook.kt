package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

enum class BookStatus { PENDING, READING, EXAMINATION, COMPLETED }
data class ChildBook(
    val id: Long,
    val childId: Long,
    val bookId: Long,
    val status: BookStatus,
    val assignedAt: Timestamp,
    val completedAt: Timestamp?
)
