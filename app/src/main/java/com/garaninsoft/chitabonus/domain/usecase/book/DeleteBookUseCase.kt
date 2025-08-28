package com.garaninsoft.chitabonus.domain.usecase.book
import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) = repository.deleteBook(book)
}