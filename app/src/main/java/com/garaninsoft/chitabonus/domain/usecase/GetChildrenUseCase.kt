package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Аннотация @Inject сообщает Dagger/Hilt, как предоставить экземпляр этого Use Case
class GetChildrenUseCase @Inject constructor(
    private val repository: ChildRepository
) {
    // Use Cases часто представляют собой просто invoke-функции, что позволяет вызывать их как объекты: useCase()
    operator fun invoke(): Flow<List<Child>> {
        return repository.getAllChildren()
    }
}