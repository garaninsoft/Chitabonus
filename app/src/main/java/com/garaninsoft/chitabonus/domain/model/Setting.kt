package com.garaninsoft.chitabonus.domain.model

import java.sql.Timestamp

// Enum для типов настроек
enum class SettingType {
    STRING, INT, DOUBLE, BOOLEAN, LONG
}
// Объект с ключами настроек для избежания опечаток
object SettingKeys {
    const val POINTS_PER_UNIT = "points_per_unit"
    const val CURRENCY_LABEL = "currency_label"
    // Сюда можно добавлять другие ключи по мере развития приложения
    // const val SOME_NEW_SETTING = "some_new_setting"
}
// Data class для представления одной настройки
data class Setting(
    val key: String,
    val value: String,
    val type: SettingType,
    val updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
) {
    // Вспомогательные функции для получения значения в нужном типе
    fun getIntValue(): Int = value.toIntOrNull() ?: 0
    fun getDoubleValue(): Double = value.toDoubleOrNull() ?: 0.0
    fun getBooleanValue(): Boolean = value.toBooleanStrictOrNull() ?: false
    fun getLongValue(): Long = value.toLongOrNull() ?: 0L
    // Для String просто возвращаем value
}
