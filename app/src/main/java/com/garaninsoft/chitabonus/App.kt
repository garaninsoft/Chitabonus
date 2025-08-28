package com.garaninsoft.chitabonus

import android.app.Application
import com.garaninsoft.chitabonus.domain.usecase.InitializeAppUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application(){

    @Inject
    lateinit var initializeAppUseCase: InitializeAppUseCase

    override fun onCreate() {
        super.onCreate()

        // Инициализируем данные в фоновом потоке
        CoroutineScope(Dispatchers.IO).launch {
            initializeAppUseCase()
        }
    }
}