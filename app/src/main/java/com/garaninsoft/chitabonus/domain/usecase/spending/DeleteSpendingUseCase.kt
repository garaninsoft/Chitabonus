package com.garaninsoft.chitabonus.domain.usecase.spending

import com.garaninsoft.chitabonus.domain.model.Spending
import com.garaninsoft.chitabonus.domain.repository.SpendingRepository
import javax.inject.Inject

class DeleteSpendingUseCase @Inject constructor(
    private val repository: SpendingRepository
) {
    suspend operator fun invoke(spending: Spending) =
        repository.deleteSpending(spending)
}