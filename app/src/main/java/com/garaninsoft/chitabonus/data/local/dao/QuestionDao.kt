package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE book_id = :bookId ORDER BY is_main DESC, difficulty DESC")
    fun getByBookId(bookId: Long): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getById(id: Long): QuestionEntity?

    // Для экрана проверки: получить основные вопросы для книги
    @Query("SELECT * FROM questions WHERE book_id = :bookId AND is_main = 1")
    suspend fun getMainQuestionsForBook(bookId: Long): List<QuestionEntity>

    // Для ручного добавления вопросов: получить вопросы по сложности (исключая основные, если нужно)
    @Query("SELECT * FROM questions WHERE book_id = :bookId AND difficulty BETWEEN :minDiff AND :maxDiff")
    suspend fun getQuestionsByDifficulty(bookId: Long, minDiff: Int, maxDiff: Int): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(question: QuestionEntity): Long

    @Delete
    suspend fun delete(question: QuestionEntity)
}