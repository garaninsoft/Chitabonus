package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.ChildEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {

    // Flow позволяет наблюдать за изменениями в реальном времени
    @Query("SELECT * FROM children ORDER BY name")
    fun getAll(): Flow<List<ChildEntity>>

    @Query("SELECT * FROM children WHERE id = :id")
    fun getById(id: Long): Flow<ChildEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // REPLACE при конфликте ID
    suspend fun upsert(child: ChildEntity): Long

    @Delete
    suspend fun delete(child: ChildEntity)

    // Прямое обновление баланса без загрузки всей сущности
    @Query("UPDATE children SET current_balance = :newBalance WHERE id = :childId")
    suspend fun updateBalance(childId: Long, newBalance: Double)
}