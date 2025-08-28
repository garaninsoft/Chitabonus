package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.BookEntity
import com.garaninsoft.chitabonus.domain.model.Book
import java.sql.Timestamp

// Преобразование Domain Model -> Data Entity
fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        pages = this.pages,
        coverUri = this.coverUri,
        ageFrom = this.ageFrom,
        ageTo = this.ageTo,
        timestamp = this.timestamp.time // Конвертируем Date в Long
    )
}

// Преобразование Data Entity -> Domain Model
fun BookEntity.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author,
        pages = this.pages,
        coverUri = this.coverUri,
        ageFrom = this.ageFrom,
        ageTo = this.ageTo,
        timestamp = Timestamp(this.timestamp) // Конвертируем Long обратно в Date
    )
}