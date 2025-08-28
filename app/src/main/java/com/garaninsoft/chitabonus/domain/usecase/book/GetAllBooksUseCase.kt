package com.garaninsoft.chitabonus.domain.usecase.book

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> = repository.getAllBooks()
}