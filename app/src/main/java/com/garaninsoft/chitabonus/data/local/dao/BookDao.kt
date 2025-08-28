package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY title")
    fun getAll(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getById(id: Long): Flow<BookEntity?>

    // Полезный запрос для фильтрации по возрастным группам
    @Query("SELECT * FROM books WHERE (:age >= age_from OR age_from IS NULL) AND (:age <= age_to OR age_to IS NULL) ORDER BY title")
    fun getByAgeRange(age: Int): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: BookEntity): Long

    @Delete
    suspend fun delete(book: BookEntity)

    // Для поиска книг по названию или автору
    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' ORDER BY title")
    fun searchBooks(query: String): Flow<List<BookEntity>>
}