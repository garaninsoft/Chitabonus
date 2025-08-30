package com.garaninsoft.chitabonus.ui.utils

import com.garaninsoft.chitabonus.R

object DemoAvatarProvider {
    // Список ID ресурсов из drawable
    private val demoAvatarResources = listOf(
        R.drawable.avatar1,  // Замени на реальные имена твоих файлов
        R.drawable.avatar2,
        R.drawable.avatar3
    )

    // Генерация Android Resource URI
    fun getRandomAvatarUri(): String {
        val randomResourceId = demoAvatarResources.random()
        return "android.resource://com.yourapp/$randomResourceId"
    }

    // Альтернативно: можно получать ID ресурса
    fun getRandomAvatarResourceId(): Int {
        return demoAvatarResources.random()
    }
}