package com.garaninsoft.chitabonus.domain.model

import java.sql.Date

data class Child(
    val id: Long,
    val name: String,
    val birthDate: Date,
    val avatarUri: String?,
    val currentBalance: Double
)