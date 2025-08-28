package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.SpendingEntity
import com.garaninsoft.chitabonus.domain.model.Spending
import java.sql.Timestamp

fun Spending.toEntity(): SpendingEntity {
    return SpendingEntity(
        id = this.id,
        childId = this.childId,
        amount = this.amount,
        description = this.description,
        timestamp = this.timestamp.time
    )
}

fun SpendingEntity.toDomain(): Spending {
    return Spending(
        id = this.id,
        childId = this.childId,
        amount = this.amount,
        description = this.description,
        timestamp = Timestamp(this.timestamp)
    )
}