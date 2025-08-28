package com.garaninsoft.chitabonus.data.local.mapper

import com.garaninsoft.chitabonus.data.local.entity.ChildEntity
import com.garaninsoft.chitabonus.domain.model.Child
import java.sql.Date

// Преобразуем Domain Model в Data Entity (для сохранения в БД)
fun Child.toEntity(): ChildEntity {
    return ChildEntity(
        id = this.id,
        name = this.name,
        birthDate = this.birthDate.time, // Конвертируем LocalDate в Long
        avatarUri = this.avatarUri,
        currentBalance = this.currentBalance
    )
}

// Преобразуем Data Entity в Domain Model (для возврата в Domain слой)
fun ChildEntity.toDomain(): Child {
    return Child(
        id = this.id,
        name = this.name,
        birthDate = Date(this.birthDate), // Конвертируем Long обратно в Date
        avatarUri = this.avatarUri,
        currentBalance = this.currentBalance
    )
}