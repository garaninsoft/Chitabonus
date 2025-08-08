package com.garaninsoft.chitabonus.ui.screens.review


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.garaninsoft.chitabonus.viewmodel.BookListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    viewModel: BookListViewModel = hiltViewModel(),
    onBack: () -> Unit,
    bookId: String?
) {
    // Если bookId null — покажем сообщение
    if (bookId == null) {
        Scaffold(topBar = { TopAppBar(title = { Text("Проверка книги") }) }) { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
                Text("Не выбрана книга для проверки")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onBack) { Text("Назад") }
            }
        }
        return
    }

    // Установим выбранную книгу
    LaunchedEffect(bookId) {
        viewModel.selectBook(bookId)
    }

    val selected by viewModel.selectedBook.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Проверка книги") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            if (selected == null) {
                Text("Книга не найдена")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onBack) { Text("Назад") }
                return@Column
            }

            Text(text = selected!!.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Автор: ${selected!!.author ?: "—"}")
            Spacer(modifier = Modifier.height(12.dp))

            Text("Ответы ребёнка:")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Кто главный герой: ${selected!!.answers?.mainCharacter ?: "—"}")
            Text("Что понравилось: ${selected!!.answers?.likedWhat ?: "—"}")
            Text("Краткий пересказ: ${selected!!.answers?.summary ?: "—"}")

            Spacer(modifier = Modifier.height(16.dp))

            var rewardText by remember { mutableStateOf("") }
            OutlinedTextField(value = rewardText, onValueChange = { rewardText = it }, label = { Text("Награда (сумма/баллы/подарок)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    viewModel.acceptBook(selected!!.id, rewardText.ifBlank { "Поощрение" })
                    onBack()
                }) { Text("Принять и выдать награду") }

                Button(onClick = {
                    viewModel.rejectBook(selected!!.id, null)
                    onBack()
                }) { Text("Отклонить") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) { Text("Назад") }
        }
    }
}
