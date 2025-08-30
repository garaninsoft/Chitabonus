package com.garaninsoft.chitabonus.domain.model

import java.util.Date

data class Child(
    val id: Long = 0,
    val name: String,
    val birthDate: Date,
    val avatarUri: String? = null,
    val currentBalance: Double
)