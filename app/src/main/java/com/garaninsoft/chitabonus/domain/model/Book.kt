package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

data class Book(
    val id: Long = 0,
    val title: String,
    val author: String?,
    val pages: Int?,
    val coverUri: String? = null,
    val ageFrom: Int?,
    val ageTo: Int?,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
)