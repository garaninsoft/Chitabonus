package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.dao.ChildBookWithBook
import com.garaninsoft.chitabonus.data.local.entity.ChildBookEntity
import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.BookStatus
import com.garaninsoft.chitabonus.domain.model.ChildBook
import java.sql.Timestamp

// --- Маппинг для ChildBookEntity <-> ChildBook ---

// Вспомогательная функция для конвертации enum Status в Int для БД
fun BookStatus.toInt(): Int {
    return when (this) {
        BookStatus.PENDING -> 0
        BookStatus.READING -> 1
        BookStatus.EXAMINATION -> 2
        BookStatus.COMPLETED -> 3
    }
}

// Вспомогательная функция для конвертации Int из БД в enum Status
fun Int.toChildBookStatus(): BookStatus {
    return when (this) {
        0 -> BookStatus.PENDING
        1 -> BookStatus.READING
        2 -> BookStatus.EXAMINATION
        3 -> BookStatus.COMPLETED
        else -> throw IllegalArgumentException("Unknown status value: $this")
    }
}

// Domain Model -> Data Entity
fun ChildBook.toEntity(): ChildBookEntity {
    return ChildBookEntity(
        id = this.id,
        childId = this.childId,
        bookId = this.bookId,
        status = this.status.toInt(),
        assignedAt = this.assignedAt.time,
        completedAt = this.completedAt?.time
    )
}

// Data Entity -> Domain Model
fun ChildBookEntity.toDomain(): ChildBook {
    return ChildBook(
        id = this.id,
        childId = this.childId,
        bookId = this.bookId,
        status = this.status.toChildBookStatus(),
        assignedAt = Timestamp(this.assignedAt),
        completedAt = this.completedAt?.let { Timestamp(it) }
    )
}

// --- Маппинг для результата JOIN-запроса (ChildBookWithBook) -> Pair<Book, ChildBook> ---
fun ChildBookWithBook.toDomainPair(): Pair<Book, ChildBook> {
    // Создаём Domain Model Book из полей JOIN-запроса
    val book = Book(
        id = this.bookId,
        title = this.bookTitle,
        author = this.bookAuthor,
        coverUri = this.bookCoverUri,
        pages = this.bookPages,
        // Остальные поля не участвуют в запросе, поэтому null
        ageFrom = null,
        ageTo = null,
        timestamp = Timestamp(System.currentTimeMillis()))

    // Создаём Domain Model ChildBook из полей JOIN-запроса
    val childBook = ChildBook(
        id = this.id,
        childId = this.childId,
        bookId = this.bookId,
        status = this.status.toChildBookStatus(),
        assignedAt = Timestamp(this.assignedAt),
        completedAt = this.completedAt?.let { Timestamp(it) }
    )

    return Pair(book, childBook)
}