package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.SpendingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Insert
    suspend fun insert(spending: SpendingEntity): Long

    @Update
    suspend fun update(spending: SpendingEntity)

    @Delete
    suspend fun delete(spending: SpendingEntity)

    @Query("SELECT * FROM spendings WHERE child_id = :childId ORDER BY timestamp DESC")
    fun getByChildId(childId: Long): Flow<List<SpendingEntity>>

    @Query("SELECT * FROM spendings WHERE id = :id")
    suspend fun getById(id: Long): SpendingEntity?

    // Для подсчёта общей суммы трат (может пригодиться для аналитики)
    @Query("SELECT SUM(amount) FROM spendings WHERE child_id = :childId")
    fun getTotalSpentByChild(childId: Long): Flow<Double>
}