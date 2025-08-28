package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.ChildBookEntity
import kotlinx.coroutines.flow.Flow

// Data class для результата JOIN-запроса.
// Будет содержать данные и о книге, и о её статусе у ребёнка.
data class ChildBookWithBook(
    // Поля из ChildBookEntity
    val id: Long,
    @ColumnInfo(name = "child_id")
    val childId: Long,
    @ColumnInfo(name = "book_id")
    val bookId: Long,
    val status: Int,
    @ColumnInfo(name = "assigned_at")
    val assignedAt: Long,
    @ColumnInfo(name = "completed_at")
    val completedAt: Long?,
    // Поля из BookEntity
    val bookTitle: String,
    val bookAuthor: String?,
    val bookCoverUri: String?,
    val bookPages: Int?
)

@Dao
interface ChildBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(childBook: ChildBookEntity): Long

    @Query("SELECT * FROM child_books WHERE id = :id")
    suspend fun getById(id: Long): ChildBookEntity?

    @Query("SELECT * FROM child_books WHERE child_id = :childId AND book_id = :bookId")
    suspend fun getByChildAndBook(childId: Long, bookId: Long): ChildBookEntity?

    @Query("UPDATE child_books SET status = :newStatus WHERE id = :childBookId")
    suspend fun updateStatus(childBookId: Long, newStatus: Int)

    @Query("UPDATE child_books SET completed_at = :timestamp WHERE id = :childBookId")
    suspend fun setCompletedTimestamp(childBookId: Long, timestamp: Long)

    // САМЫЙ ВАЖНЫЙ ЗАПРОС: Получить все книги конкретного ребёнка вместе с их данными и статусом.
    @Query(
        """
        SELECT 
            cb.id, 
            cb.child_id, 
            cb.book_id, 
            cb.status, 
            cb.assigned_at, 
            cb.completed_at,
            b.title as bookTitle,
            b.author as bookAuthor,
            b.cover_uri as bookCoverUri,
            b.pages as bookPages
        FROM child_books cb
        INNER JOIN books b ON cb.book_id = b.id
        WHERE cb.child_id = :childId
        ORDER BY cb.status, cb.assigned_at DESC
        """
    )
    fun getBooksWithDetailsForChild(childId: Long): Flow<List<ChildBookWithBook>>

    // Для статистики: подсчёт завершённых кник
    @Query("SELECT COUNT(*) FROM child_books WHERE child_id = :childId AND status = 3") // 3 = COMPLETED
    fun getCompletedBooksCount(childId: Long): Flow<Int>
}