package com.garaninsoft.chitabonus.domain.repository

import com.garaninsoft.chitabonus.domain.model.Spending
import kotlinx.coroutines.flow.Flow

interface SpendingRepository {
    fun getSpendingsForChild(childId: Long): Flow<List<Spending>>
    suspend fun getSpendingById(id: Long): Spending?
    suspend fun addSpending(spending: Spending): Long
    suspend fun updateSpending(spending: Spending)
    suspend fun deleteSpending(spending: Spending)
    fun getTotalSpentByChild(childId: Long): Flow<Double>
}