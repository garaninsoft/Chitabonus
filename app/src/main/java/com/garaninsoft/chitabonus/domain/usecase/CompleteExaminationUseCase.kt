package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.domain.repository.AnswerRepository
import javax.inject.Inject

class CompleteExaminationUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {
    // Use Case скрывает сложность операции завершения попытки от ViewModel
    suspend operator fun invoke(childBookId: Long): Boolean {
        return answerRepository.completeCurrentAttempt(childBookId)
    }
}