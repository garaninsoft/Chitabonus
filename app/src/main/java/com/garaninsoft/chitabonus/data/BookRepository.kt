package com.garaninsoft.chitabonus.data

import com.garaninsoft.chitabonus.data.model.Book
import com.garaninsoft.chitabonus.data.storage.BookStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BookRepository(private val storage: BookStorage) {

    fun getBooks(): Flow<List<Book>> = storage.getBooks()

    suspend fun addBook(book: Book) {
        val current = storage.getBooks().first()
        storage.saveBooks(current + book)
    }

    suspend fun updateBook(updated: Book) {
        val current = storage.getBooks().first()
        val newList = current.map { if (it.id == updated.id) updated else it }
        storage.saveBooks(newList)
    }

    suspend fun deleteBook(id: String) {
        val current = storage.getBooks().first()
        storage.saveBooks(current.filterNot { it.id == id })
    }
}