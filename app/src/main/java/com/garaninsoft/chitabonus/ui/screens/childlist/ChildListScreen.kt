package com.garaninsoft.chitabonus.ui.screens.childlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.garaninsoft.chitabonus.domain.model.Child
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import com.garaninsoft.chitabonus.ui.components.ChildCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildListScreen(
    onAddChild: () -> Unit,
    onChildClick: (Long) -> Unit,
    viewModel: ChildListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Показываем ошибки в snackbar
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            // Очищаем ошибку через ViewModel
            scope.launch {
                viewModel.clearError()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Дети") },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Обновить")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddChild) {
                Icon(Icons.Default.Add, contentDescription = "Добавить ребёнка")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.children.isEmpty()) {
                EmptyState()
            } else {
                ChildList(
                    children = state.children,
                    pointsPerUnit = state.pointsPerUnit,
                    onChildClick = onChildClick
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Нет добавленных детей",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = "Нажмите + чтобы добавить первого ребёнка",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ChildList(
    children: List<Child>,
    pointsPerUnit: Double,
    onChildClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(children) { child ->
            ChildCard(
                child = child,
                pointsPerUnit = pointsPerUnit,
                onClick = { onChildClick(child.id) }
            )
        }
    }
}

//@Composable
//private fun ChildCard(
//    child: Child,
//    pointsPerUnit: Double,
//    onClick: () -> Unit
//) {
//    // Временная реализация - потом заменим на красивую карточку
//    Box(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxSize()
//    ) {
//        Text(
//            text = "${child.name} - ${child.currentBalance} баллов",
//            style = MaterialTheme.typography.bodyLarge
//        )
//    }
//}

@Preview
@Composable
fun PreviewChildListScreen() {
    MaterialTheme {
        ChildListScreen(
            onAddChild = {},
            onChildClick = {}
        )
    }
}