package com.garaninsoft.chitabonus.domain.usecase.settings

import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import javax.inject.Inject

class SetCurrencyLabelUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: String) = repository.setCurrencyLabel(value)
}