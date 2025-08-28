package com.garaninsoft.chitabonus.domain.repository

import com.garaninsoft.chitabonus.domain.model.Answer
import com.garaninsoft.chitabonus.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface AnswerRepository {
    // Этот репозиторий будет центральным для сложной логики проверки (EXAMINATION)

    // Начать новую попытку проверки для связки ChildBook.
    // Возвращает номер попытки (attempts)
    suspend fun startNewAttempt(childBookId: Long): Int

    // Получить все вопросы для текущей попытки (и основные, и добавленные вручную)
    fun getQuestionsForCurrentAttempt(childBookId: Long): Flow<List<QuestionWithAnswer>>

    // Сохранить ответ родителя (ответ ребёнка был верный или неверный) на вопрос в текущей попытке
    suspend fun submitAnswerForCurrentAttempt(
        childBookId: Long,
        questionId: Long,
        isCorrect: Boolean,
        childAnswer: String?
    )

    // Завершить текущую попытку. Здесь происходит основная логика начисления баллов.
    // Возвращает успешность (true - если попытка успешна и книга завершена, false - если нужна новая попытка)
    suspend fun completeCurrentAttempt(childBookId: Long): Boolean

    // Data class для удобства: вопрос + сохранённый на него ответ (если есть)
    data class QuestionWithAnswer(
        val question: Question,
        val answer: Answer? // null, если на вопрос ещё не ответили
    )
}