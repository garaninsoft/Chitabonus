package com.garaninsoft.chitabonus.domain.usecase.question

import com.garaninsoft.chitabonus.domain.model.Question
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsForBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: Long): Flow<List<Question>> =
        repository.getQuestionsForBook(bookId)
}