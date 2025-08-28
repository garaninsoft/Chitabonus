package com.garaninsoft.chitabonus.data.repository

import com.garaninsoft.chitabonus.data.local.dao.BookDao
import com.garaninsoft.chitabonus.data.local.dao.QuestionDao
import com.garaninsoft.chitabonus.data.local.mapper.toDomain
import com.garaninsoft.chitabonus.data.local.mapper.toEntity
import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.Question
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val questionDao: QuestionDao
) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getBookById(bookId: Long): Flow<Book?> {
        return bookDao.getById(bookId).map { it?.toDomain() }
    }

    override fun getByAgeRange(age: Int): Flow<List<Book>> {
        return bookDao.getByAgeRange(age).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun upsertBook(book: Book): Long {
        return bookDao.upsert(book.toEntity())
    }

    override suspend fun deleteBook(book: Book) {
        bookDao.delete(book.toEntity())
    }

    override fun searchBooks(query: String): Flow<List<Book>> {
        return bookDao.searchBooks(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    // Questions
    override fun getQuestionsForBook(bookId: Long): Flow<List<Question>> {
        return questionDao.getByBookId(bookId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun upsertQuestion(question: Question): Long {
        return questionDao.upsert(question.toEntity())
    }

    override suspend fun deleteQuestion(question: Question) {
        questionDao.delete(question.toEntity())
    }

    override suspend fun getQuestionById(questionId: Long): Question? {
        return questionDao.getById(questionId)?.toDomain()
    }

    override suspend fun getMainQuestionsForBook(bookId: Long): List<Question> {
        return questionDao.getMainQuestionsForBook(bookId).map { it.toDomain() }
    }

    override suspend fun getQuestionsByDifficulty(bookId: Long, minDiff: Int, maxDiff: Int): List<Question> {
        return questionDao.getQuestionsByDifficulty(bookId, minDiff, maxDiff).map { it.toDomain() }
    }
}