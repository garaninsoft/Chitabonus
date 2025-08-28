package com.garaninsoft.chitabonus.data.repository

import com.garaninsoft.chitabonus.data.local.dao.AnswerDao
import com.garaninsoft.chitabonus.data.local.dao.ChildBookDao
import com.garaninsoft.chitabonus.data.local.dao.ChildDao
import com.garaninsoft.chitabonus.data.local.dao.QuestionDao
import com.garaninsoft.chitabonus.data.local.entity.AnswerEntity
import com.garaninsoft.chitabonus.data.local.mapper.toDomain
import com.garaninsoft.chitabonus.data.local.mapper.toInt
import com.garaninsoft.chitabonus.domain.model.BookStatus
import com.garaninsoft.chitabonus.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnswerRepositoryImpl @Inject constructor(
    private val answerDao: AnswerDao,
    private val questionDao: QuestionDao,
    private val childBookDao: ChildBookDao,
    private val childDao: ChildDao
) : AnswerRepository {

    // Временное хранилище для вопросов текущей попытки
    private val currentAttempts = mutableMapOf<Long, Int>() // childBookId -> attemptNumber
    private val currentAttemptQuestions = mutableMapOf<Long, MutableList<Long>>() // childBookId -> questionIds

    override suspend fun startNewAttempt(childBookId: Long): Int {
        // 1. Получаем максимальный номер предыдущей попытки
        val previousAttempt = answerDao.getMaxAttemptNumber(childBookId) ?: 0
        val newAttemptNumber = previousAttempt + 1

        // 2. Получаем связь ребёнок-книга чтобы узнать bookId
        val childBook = childBookDao.getById(childBookId)
            ?: throw IllegalArgumentException("ChildBook not found")

        // 3. Формируем список вопросов для этой попытки
        val questionsForAttempt = if (newAttemptNumber == 1) {
            // Первая попытка - берём только основные вопросы
            questionDao.getMainQuestionsForBook(childBook.bookId)
        } else {
            // TODO: Последующие попытки - нужно реализовать логику выбора
            // дополнительных вопросов по сложности (пока заглушка)
            emptyList()
        }

        // 4. Сохраняем и номер попытки, и вопросы
        currentAttempts[childBookId] = newAttemptNumber
        currentAttemptQuestions[childBookId] = questionsForAttempt.map { it.id }.toMutableList()

        return newAttemptNumber
    }

    override fun getQuestionsForCurrentAttempt(childBookId: Long): Flow<List<AnswerRepository.QuestionWithAnswer>> {
        val attemptNumber = currentAttempts[childBookId]
            ?: return flowOf(emptyList()) // Нет активной попытки

        val questionIds = currentAttemptQuestions[childBookId] ?: emptyList()

        // Получаем уже сохранённые ответы для этой попытки (если родитель уже что-то отмечал)
        return answerDao.getAnswersForAttempt(childBookId, attemptNumber).map { answers ->
            questionIds.mapNotNull { questionId ->
                val answerEntity = answers.find { it.questionId == questionId }
                val questionEntity = questionDao.getById(questionId)?.toDomain()

                if (questionEntity != null) {
                    AnswerRepository.QuestionWithAnswer(
                        question = questionEntity,
                        answer = answerEntity?.toDomain()
                    )
                } else {
                    null
                }
            }
        }
    }

    override suspend fun submitAnswerForCurrentAttempt(
        childBookId: Long,
        questionId: Long,
        isCorrect: Boolean,
        childAnswer: String?
    ) {
        // 1. Получаем номер текущей попытки
        val attemptNumber = answerDao.getMaxAttemptNumber(childBookId)
            ?: throw IllegalStateException("No active attempt")

        // 2. Создаём или обновляем ответ
        val existingAnswer = answerDao.getAnswerForQuestionInAttempt(questionId, childBookId, attemptNumber)

        val answerEntity = if (existingAnswer != null) {
            existingAnswer.copy(
                isCorrect = isCorrect,
                childAnswer = childAnswer
            )
        } else {
            AnswerEntity(
                questionId = questionId,
                childBookId = childBookId,
                childAnswer = childAnswer,
                isCorrect = isCorrect,
                attempts = attemptNumber
            )
        }

        answerDao.upsert(answerEntity)
    }

    override suspend fun completeCurrentAttempt(childBookId: Long): Boolean {
        val attemptNumber = currentAttempts[childBookId]
            ?: throw IllegalStateException("No active attempt")

        // 1. Получаем все ответы этой попытки
        val answers = answerDao.getAnswersForAttempt(childBookId, attemptNumber)
            .first() // Convert Flow to List

        // 2. Проверяем, что на все вопросы ответили
        val unansweredQuestions = answers.any { it.isCorrect == null }
        if (unansweredQuestions) {
            throw IllegalStateException("Not all questions answered")
        }

        // 3. Подсчитываем баллы по формуле: difficulty / attempts
        var totalPoints = 0.0
        val childBook = childBookDao.getById(childBookId)
            ?: throw IllegalArgumentException("ChildBook not found")

        for (answer in answers) {
            if (answer.isCorrect == true) {
                val question = questionDao.getById(answer.questionId)
                    ?: continue
                val pointsForQuestion = question.difficulty.toDouble() / attemptNumber
                totalPoints += pointsForQuestion
            }
        }

        // 4. Обновляем баланс ребёнка
        val child = childDao.getById(childBook.childId).first()
            ?: throw IllegalArgumentException("Child not found")

        val newBalance = child.currentBalance + totalPoints
        childDao.updateBalance(childBook.childId, newBalance)

        // 5. Проверяем результат попытки
        val allCorrect = answers.all { it.isCorrect == true }

        if (allCorrect) {
            // Успешная попытка - помечаем книгу как завершённую
            childBookDao.updateStatus(childBookId, BookStatus.COMPLETED.toInt())
            childBookDao.setCompletedTimestamp(childBookId, System.currentTimeMillis())
        }

        // 6. Очищаем кэш вопросов для этой попытки
        currentAttempts.remove(childBookId)
        currentAttemptQuestions.remove(childBookId)

        return allCorrect
    }

    // Если родитель прервёт проверку - просто очищаем память
    suspend fun cancelAttempt(childBookId: Long) {
        val attemptNumber = currentAttempts[childBookId] ?: return

        // Удаляем все ответы этой незавершённой попытки из БД
        answerDao.deleteAttemptAnswers(childBookId, attemptNumber)

        // Очищаем память
        currentAttempts.remove(childBookId)
        currentAttemptQuestions.remove(childBookId)
    }
}