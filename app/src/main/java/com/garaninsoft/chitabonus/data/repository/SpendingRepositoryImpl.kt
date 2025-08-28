package com.garaninsoft.chitabonus.data.repository

import com.garaninsoft.chitabonus.data.local.dao.SpendingDao
import com.garaninsoft.chitabonus.data.local.mapper.toDomain
import com.garaninsoft.chitabonus.data.local.mapper.toEntity
import com.garaninsoft.chitabonus.domain.model.Spending
import com.garaninsoft.chitabonus.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val spendingDao: SpendingDao
) : SpendingRepository {

    override fun getSpendingsForChild(childId: Long): Flow<List<Spending>> {
        return spendingDao.getByChildId(childId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSpendingById(id: Long): Spending? {
        return spendingDao.getById(id)?.toDomain()
    }

    override suspend fun addSpending(spending: Spending): Long {
        return spendingDao.insert(spending.toEntity())
    }

    override suspend fun updateSpending(spending: Spending) {
        spendingDao.update(spending.toEntity())
    }

    override suspend fun deleteSpending(spending: Spending) {
        spendingDao.delete(spending.toEntity())
    }

    override fun getTotalSpentByChild(childId: Long): Flow<Double> {
        return spendingDao.getTotalSpentByChild(childId)
    }
}