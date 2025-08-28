package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

data class Book(
    val id: Long,
    val title: String,
    val author: String?,
    val pages: Int?,
    val coverUri: String?,
    val ageFrom: Int?,
    val ageTo: Int?,
    val timestamp: Timestamp
)