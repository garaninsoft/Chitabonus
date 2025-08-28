package com.garaninsoft.chitabonus.domain.usecase.settings

import com.garaninsoft.chitabonus.domain.model.Setting
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(setting: Setting) {
        repository.saveSetting(setting)
    }
}