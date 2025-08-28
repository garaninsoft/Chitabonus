package com.garaninsoft.chitabonus.domain.repository

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.BookStatus
import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.model.ChildBook
import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    // Получить поток (Flow) со списком всех детей для автоматического обновления UI
    fun getAllChildren(): Flow<List<Child>>

    // Получить конкретного ребёнка по ID
    fun getChildById(childId: Long): Flow<Child?>

    // Вставить или обновить ребёнка (возвращает ID новой записи или количество обновлённых строк)
    suspend fun upsertChild(child: Child): Long

    // Удалить ребёнка
    suspend fun deleteChild(child: Child)

    // Получить список книг, назначенных конкретному ребёнку, вместе с их статусом (ChildBook)
    fun getBooksForChild(childId: Long): Flow<List<Pair<Book, ChildBook>>>

    // Назначить книгу ребёнку (создать запись ChildBook со статусом PENDING)
    suspend fun assignBookToChild(childId: Long, bookId: Long)

    // Обновить статус книги у ребёнка (например, поменять PENDING на READING)
    suspend fun updateChildBookStatus(childBookId: Long, newStatus: BookStatus)

    // Получить конкретную связь "ребёнок-книга"
    suspend fun getChildBook(childId: Long, bookId: Long): ChildBook?

    // Добавить трату и списать баллы с баланса ребёнка
    suspend fun addSpending(childId: Long, amount: Double, description: String)
}