package com.garaninsoft.chitabonus.domain.usecase.spending

import com.garaninsoft.chitabonus.domain.model.Spending
import com.garaninsoft.chitabonus.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpendingsUseCase @Inject constructor(
    private val repository: SpendingRepository
) {
    operator fun invoke(childId: Long): Flow<List<Spending>> =
        repository.getSpendingsForChild(childId)
}