package com.garaninsoft.chitabonus.domain.usecase.settings

import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import javax.inject.Inject

class SetPointsPerUnitUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Double) = repository.setPointsPerUnit(value)
}