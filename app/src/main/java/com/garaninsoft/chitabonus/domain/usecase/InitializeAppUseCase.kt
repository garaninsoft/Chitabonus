package com.garaninsoft.chitabonus.domain.usecase

import com.garaninsoft.chitabonus.data.initial.InitialDataManager
import javax.inject.Inject

class InitializeAppUseCase @Inject constructor(
    private val initialDataManager: InitialDataManager
) {
    suspend operator fun invoke() {
        initialDataManager.initializeIfNeeded()
    }
}