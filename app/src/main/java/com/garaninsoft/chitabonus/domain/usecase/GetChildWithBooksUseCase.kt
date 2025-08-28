package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.ChildBook
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChildWithBooksUseCase @Inject constructor(
    private val repository: ChildRepository
) {
    operator fun invoke(childId: Long): Flow<List<Pair<Book, ChildBook>>> {
        return repository.getBooksForChild(childId)
    }
}