package com.garaninsoft.chitabonus.data.repository

import com.garaninsoft.chitabonus.data.local.dao.BookDao
import com.garaninsoft.chitabonus.data.local.dao.ChildBookDao
import com.garaninsoft.chitabonus.data.local.dao.ChildDao
import com.garaninsoft.chitabonus.data.local.entity.ChildBookEntity
import com.garaninsoft.chitabonus.data.local.mapper.toDomain
import com.garaninsoft.chitabonus.data.local.mapper.toDomainPair
import com.garaninsoft.chitabonus.data.local.mapper.toEntity
import com.garaninsoft.chitabonus.data.local.mapper.toInt
import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.BookStatus
import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.model.ChildBook
import com.garaninsoft.chitabonus.domain.repository.ChildRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// @Inject сообщает Hilt, как предоставить эту реализацию для интерфейса ChildRepository
class ChildRepositoryImpl @Inject constructor(
    private val childDao: ChildDao,
    private val bookDao: BookDao,
    private val childBookDao: ChildBookDao
) : ChildRepository {

    override fun getAllChildren(): Flow<List<Child>> {
        // Преобразуем Flow<List<ChildEntity>> в Flow<List<Child>>
        return childDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getChildById(childId: Long): Flow<Child?> {
        return childDao.getById(childId).map { it?.toDomain() }
    }

    override suspend fun upsertChild(child: Child): Long {
        return childDao.upsert(child.toEntity())
    }

    override suspend fun deleteChild(child: Child) {
        childDao.delete(child.toEntity())
    }

    override fun getBooksForChild(childId: Long): Flow<List<Pair<Book, ChildBook>>> {
        // Используем наш JOIN-запрос и маппер для преобразования результата
        return childBookDao.getBooksWithDetailsForChild(childId)
            .map { list -> list.map { it.toDomainPair() } }
    }

    override suspend fun assignBookToChild(childId: Long, bookId: Long) {
        // Создаём новую связь ChildBook со статусом PENDING
        val newChildBookEntity = ChildBookEntity(
            childId = childId,
            bookId = bookId,
            status = BookStatus.PENDING.toInt(), // Используем наш маппер
            assignedAt = System.currentTimeMillis(),
            completedAt = null
        )
        childBookDao.upsert(newChildBookEntity)
    }

    override suspend fun updateChildBookStatus(childBookId: Long, newStatus: BookStatus) {
        // Обновляем только статус, используя маппинг Enum -> Int
        childBookDao.updateStatus(childBookId, newStatus.toInt())

        // Если статус меняется на COMPLETED, проставляем timestamp завершения
        if (newStatus == BookStatus.COMPLETED) {
            childBookDao.setCompletedTimestamp(childBookId, System.currentTimeMillis())
        }
    }

    override suspend fun getChildBook(childId: Long, bookId: Long): ChildBook? {
        // Ищем конкретную связь
        return childBookDao.getByChildAndBook(childId, bookId)?.toDomain()
    }

    override suspend fun addSpending(childId: Long, amount: Double, description: String) {
        // 1. Получаем текущего ребёнка (Entity)
        val childEntity = childDao.getById(childId).first() // Flow -> first()
            ?: throw IllegalArgumentException("Child with id $childId not found")

        // 2. Проверяем, что хватает баллов
        if (amount > childEntity.currentBalance) {
            throw IllegalStateException("Недостаточно баллов. Текущий баланс: ${childEntity.currentBalance}")
        }

        // 3. Вычитаем сумму траты из баланса
        val newBalance = childEntity.currentBalance - amount
        childDao.updateBalance(childId, newBalance)

        // 4. TODO: Здесь позже нужно будет создать запись SpendingEntity
        // spendingDao.insert(SpendingEntity(...))
    }
}