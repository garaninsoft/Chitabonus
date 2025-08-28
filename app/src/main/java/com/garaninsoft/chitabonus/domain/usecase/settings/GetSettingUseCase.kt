package com.garaninsoft.chitabonus.domain.usecase.settings

import com.garaninsoft.chitabonus.domain.model.Setting
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(key: String): Flow<Setting?> {
        return repository.getSetting(key)
    }
}