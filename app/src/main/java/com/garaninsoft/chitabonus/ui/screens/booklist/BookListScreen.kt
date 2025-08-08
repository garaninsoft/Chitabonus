package com.garaninsoft.chitabonus.ui.screens.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.garaninsoft.chitabonus.viewmodel.BookListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: BookListViewModel = hiltViewModel(),
    onAddBookClick: () -> Unit,
    onReviewClick: (String) -> Unit
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Прочитанные книги") }) },
        floatingActionButton = { FloatingActionButton(onClick = onAddBookClick) { Text("+") } }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            items(books, key = { it.id }) { book ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onReviewClick(book.id) }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = book.author ?: "— неизвестен", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(text = "Статус: ${book.status}")
                            Text(text = "Награда: ${book.reward ?: "—"}")
                        }
                    }
                }
            }
        }
    }
}