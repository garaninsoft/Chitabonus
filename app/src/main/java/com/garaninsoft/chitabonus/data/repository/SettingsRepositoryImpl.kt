package com.garaninsoft.chitabonus.data.repository

import com.garaninsoft.chitabonus.data.local.dao.SettingsDao
import com.garaninsoft.chitabonus.data.local.mapper.toDomain
import com.garaninsoft.chitabonus.data.local.mapper.toEntity
import com.garaninsoft.chitabonus.data.local.mapper.toSettingValue
import com.garaninsoft.chitabonus.domain.model.Setting
import com.garaninsoft.chitabonus.domain.model.SettingKeys
import com.garaninsoft.chitabonus.domain.model.SettingType
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    override fun getAllSettings(): Flow<List<Setting>> {
        return settingsDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getSetting(key: String): Flow<Setting?> {
        return settingsDao.getByKey(key).map { it?.toDomain() }
    }

    override suspend fun saveSetting(setting: Setting) {
        settingsDao.upsert(setting.toEntity())
    }

    override suspend fun saveSettings(settings: List<Setting>) {
        settingsDao.upsertAll(settings.map { it.toEntity() })
    }

    override suspend fun getPointsPerUnit(): Double {
        val setting = settingsDao.getByKey(SettingKeys.POINTS_PER_UNIT).first()?.toDomain()
        return setting?.getDoubleValue() ?: 1.0 // Значение по умолчанию
    }

    override suspend fun getCurrencyLabel(): String {
        val setting = settingsDao.getByKey(SettingKeys.CURRENCY_LABEL).first()?.toDomain()
        return setting?.value ?: "₽" // Значение по умолчанию
    }

    override suspend fun setPointsPerUnit(value: Double) {
        val setting = Setting(
            key = SettingKeys.POINTS_PER_UNIT,
            value = value.toSettingValue(),
            type = SettingType.DOUBLE
        )
        saveSetting(setting)
    }

    override suspend fun setCurrencyLabel(value: String) {
        val setting = Setting(
            key = SettingKeys.CURRENCY_LABEL,
            value = value.toSettingValue(),
            type = SettingType.STRING
        )
        saveSetting(setting)
    }
}