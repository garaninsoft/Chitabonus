package com.garaninsoft.chitabonus.data.local.dao

import androidx.room.*
import com.garaninsoft.chitabonus.data.local.entity.SettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings")
    fun getAll(): Flow<List<SettingEntity>>

    @Query("SELECT * FROM settings WHERE key = :key")
    fun getByKey(key: String): Flow<SettingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(setting: SettingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(settings: List<SettingEntity>)

    @Delete
    suspend fun delete(setting: SettingEntity)

    @Query("DELETE FROM settings WHERE key = :key")
    suspend fun deleteByKey(key: String)

    @Query("DELETE FROM settings")
    suspend fun clearAll()
}