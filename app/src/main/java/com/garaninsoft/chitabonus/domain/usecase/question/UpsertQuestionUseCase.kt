package com.garaninsoft.chitabonus.domain.usecase.question

import com.garaninsoft.chitabonus.domain.model.Question
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import javax.inject.Inject

class UpsertQuestionUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(question: Question): Long =
        repository.upsertQuestion(question)
}