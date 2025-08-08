package com.garaninsoft.chitabonus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garaninsoft.chitabonus.data.BookRepository
import com.garaninsoft.chitabonus.data.model.Answers
import com.garaninsoft.chitabonus.data.model.Book
import com.garaninsoft.chitabonus.data.model.BookStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(private val repo: BookRepository) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getBooks().collect { list ->
                _books.value = list
            }
        }
    }

    fun addBook(title: String, author: String?, pages: Int? = null, coverUri: String? = null, answers: Map<String, String?>? = null) {
        viewModelScope.launch {
            val book = Book(
                id = UUID.randomUUID().toString(),
                title = title,
                author = author,
                pages = pages,
                coverUri = coverUri,
                answers = if (answers == null) null else Answers(
                    mainCharacter = answers["mainCharacter"],
                    likedWhat = answers["likedWhat"],
                    summary = answers["summary"]
                )
            )
            repo.addBook(book)

        }
    }

    fun selectBook(id: String) {

        viewModelScope.launch {
            val currentBooks = repo.getBooks().first() // suspend — безопасно в корутине
            val found = currentBooks.firstOrNull { it.id == id }
            _selectedBook.value = found
        }
    }

    fun acceptBook(id: String, reward: String) {
        viewModelScope.launch {
            val book = _books.value.firstOrNull { it.id == id } ?: return@launch
            val updated = book.copy(status = BookStatus.ACCEPTED, reward = reward)
            repo.updateBook(updated)
            _selectedBook.value = updated
        }
    }

    fun rejectBook(id: String, comment: String?) {
        viewModelScope.launch {
            val book = _books.value.firstOrNull { it.id == id } ?: return@launch
            val updated = book.copy(status = BookStatus.REJECTED)
            repo.updateBook(updated)
            _selectedBook.value = updated
            // comment can be stored later in a new field if needed
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch {
            repo.deleteBook(id)
            if (_selectedBook.value?.id == id) _selectedBook.value = null
        }
    }
}