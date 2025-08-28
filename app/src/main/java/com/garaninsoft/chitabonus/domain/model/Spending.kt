package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

data class Spending(
    val id: Long,
    val childId: Long,
    val amount: Double,
    val description: String,
    val timestamp: Timestamp
)
