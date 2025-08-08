package com.garaninsoft.chitabonus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun ChitabonusTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme()
    MaterialTheme(colorScheme = colors, content = content)
}
