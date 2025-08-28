package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(answer: AnswerEntity): Long

    // Для экрана проверки: получить ответы для конкретной попытки проверки
    @Query("SELECT * FROM answers WHERE child_book_id = :childBookId AND attempts = :attemptNumber")
    fun getAnswersForAttempt(childBookId: Long, attemptNumber: Int): Flow<List<AnswerEntity>>

    // Получить ответ на конкретный вопрос в конкретной попытке
    @Query("SELECT * FROM answers WHERE question_id = :questionId AND child_book_id = :childBookId AND attempts = :attemptNumber")
    suspend fun getAnswerForQuestionInAttempt(questionId: Long, childBookId: Long, attemptNumber: Int): AnswerEntity?

    // Получить максимальный номер попытки для связки ребёнок-книга
    @Query("SELECT MAX(attempts) FROM answers WHERE child_book_id = :childBookId")
    suspend fun getMaxAttemptNumber(childBookId: Long): Int?

    // Удалить все ответы для определённой попытки (может пригодиться для сброса)
    @Query("DELETE FROM answers WHERE child_book_id = :childBookId AND attempts = :attemptNumber")
    suspend fun deleteAttemptAnswers(childBookId: Long, attemptNumber: Int)
}