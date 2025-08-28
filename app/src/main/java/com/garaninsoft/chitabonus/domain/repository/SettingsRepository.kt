package com.garaninsoft.chitabonus.domain.repository

import com.garaninsoft.chitabonus.domain.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    // Получить все настройки
    fun getAllSettings(): Flow<List<Setting>>

    // Получить конкретную настройку по ключу
    fun getSetting(key: String): Flow<Setting?>

    // Сохранить настройку
    suspend fun saveSetting(setting: Setting)

    // Сохранить несколько настроек
    suspend fun saveSettings(settings: List<Setting>)

    // Удобные методы для часто используемых настроек
    suspend fun getPointsPerUnit(): Double
    suspend fun getCurrencyLabel(): String
    suspend fun setPointsPerUnit(value: Double)
    suspend fun setCurrencyLabel(value: String)
}