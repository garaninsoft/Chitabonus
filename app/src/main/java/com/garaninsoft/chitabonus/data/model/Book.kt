package com.garaninsoft.chitabonus.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val author: String? = null,
    val pages: Int? = null,
    val coverUri: String? = null,
    val answers: Answers? = null,
    val status: BookStatus = BookStatus.PENDING,
    val reward: String? = null
)

@Serializable
enum class BookStatus {
    PENDING, ACCEPTED, REJECTED
}

@Serializable
data class Answers(
    val mainCharacter: String? = null,
    val likedWhat: String? = null,
    val summary: String? = null
)