package com.garaninsoft.chitabonus.domain.usecase.book

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: Long): Flow<Book?> = repository.getBookById(bookId)
}