package com.garaninsoft.chitabonus.ui.screens.booklist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    onAddBookClick: () -> Unit,
    onReviewClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Прочитанные книги") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBookClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Text("Здесь будет список книг")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onReviewClick) {
                Text("Проверить книгу")
            }
        }
    }
}