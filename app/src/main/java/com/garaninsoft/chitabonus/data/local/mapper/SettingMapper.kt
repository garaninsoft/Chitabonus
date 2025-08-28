package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.SettingEntity
import com.garaninsoft.chitabonus.domain.model.Setting
import com.garaninsoft.chitabonus.domain.model.SettingType
import java.sql.Timestamp

fun Setting.toEntity(): SettingEntity {
    return SettingEntity(
        key = this.key,
        value = this.value,
        type = this.type.name,
        updatedAt = this.updatedAt.time
    )
}

fun SettingEntity.toDomain(): Setting {
    return Setting(
        key = this.key,
        value = this.value,
        type = SettingType.valueOf(this.type),
        updatedAt = Timestamp(this.updatedAt)
    )
}

// Вспомогательные функции для преобразования значений
fun Int.toSettingValue(): String = this.toString()
fun Double.toSettingValue(): String = this.toString()
fun Boolean.toSettingValue(): String = this.toString()
fun Long.toSettingValue(): String = this.toString()
fun String.toSettingValue(): String = this