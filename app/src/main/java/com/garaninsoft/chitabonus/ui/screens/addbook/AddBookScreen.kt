package com.garaninsoft.chitabonus.ui.screens.addbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Добавить книгу") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Название") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("Автор") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Сохранить и назад")
            }
        }
    }
}