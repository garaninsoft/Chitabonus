package com.garaninsoft.chitabonus.domain.repository

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<Book>>
    fun getBookById(bookId: Long): Flow<Book?>
    fun getByAgeRange(age: Int): Flow<List<Book>>
    fun searchBooks(query: String): Flow<List<Book>>
    suspend fun upsertBook(book: Book): Long
    suspend fun deleteBook(book: Book)

    // Questions
    fun getQuestionsForBook(bookId: Long): Flow<List<Question>>
    suspend fun upsertQuestion(question: Question): Long
    suspend fun deleteQuestion(question: Question)
    suspend fun getQuestionById(questionId: Long): Question?
    suspend fun getMainQuestionsForBook(bookId: Long): List<Question>
    suspend fun getQuestionsByDifficulty(bookId: Long, minDiff: Int, maxDiff: Int): List<Question>
}