package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import javax.inject.Inject

class AddChildUseCase @Inject constructor(
    private val repository: ChildRepository
) {
    // Use Case может содержать дополнительную валидацию перед сохранением
    suspend operator fun invoke(child: Child): Long {
        // Например, проверяем, что имя не пустое
        require(child.name.isNotBlank()) { "Имя ребёнка не может быть пустым" }
        return repository.upsertChild(child)
    }
}