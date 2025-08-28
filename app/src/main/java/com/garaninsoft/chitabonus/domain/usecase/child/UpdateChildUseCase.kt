package com.garaninsoft.chitabonus.domain.usecase.child

import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import javax.inject.Inject

class UpdateChildUseCase @Inject constructor(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(child: Child): Long = repository.upsertChild(child)
}