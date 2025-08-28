package com.garaninsoft.chitabonus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "type")
    val type: String, // "STRING", "INT", "DOUBLE", "BOOLEAN", "LONG"

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)