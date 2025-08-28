package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.domain.repository.AnswerRepository
import javax.inject.Inject

class StartExaminationUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {
    // Этот Use Case инкапсулирует логику начала проверки
    suspend operator fun invoke(childBookId: Long): Int {
        // Репозиторий сам создаст новую попытку и вернёт её номер
        return answerRepository.startNewAttempt(childBookId)
    }
}