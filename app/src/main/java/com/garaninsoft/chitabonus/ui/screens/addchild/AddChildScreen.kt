package com.garaninsoft.chitabonus.ui.screens.addchild

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.garaninsoft.chitabonus.ui.components.AvatarPicker
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChildScreen(
    navController: NavController,
    viewModel: AddChildViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Диалог для выбора даты
    val dateDialogState = rememberMaterialDialogState()
    var selectedLocalDate by remember { mutableStateOf<LocalDate?>(null) }

    val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) }

    // Обработка ошибок
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            scope.launch {
                viewModel.clearError()
            }
        }
    }

    // Обработка успешного добавления
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            snackbarHostState.showSnackbar("Ребёнок добавлен!")
            navController.popBackStack()
        }
    }

    // Диалог выбора даты
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Выбрать") {
                selectedLocalDate?.let { localDate ->
                    // Конвертируем LocalDate в Date
                    val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    viewModel.onBirthDateChange(date)
                }
            }
            negativeButton("Отмена")
        }
    ) {
        datepicker(
            initialDate = state.birthDate?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
                ?: LocalDate.now(),
            title = "Выберите дату рождения",
            allowedDateValidator = { it <= LocalDate.now() } // Нельзя выбрать будущую дату
        ) {
            selectedLocalDate = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить ребёнка") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Поле имени
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Имя ребёнка*") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Имя")
                },
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null && state.name.isBlank()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка выбора даты рождения
            OutlinedButton(
                onClick = { dateDialogState.show() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Дата рождения",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = state.birthDate?.let { dateFormatter.format(it) } ?: "Выберите дату рождения*",
                    color = if (state.birthDate == null) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AvatarPicker(
                avatarUri = state.avatarUri,
                onAvatarSelected = { uri ->
                    viewModel.onAvatarChange(uri)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                size = 100
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Нажмите на аватар для выбора",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка добавления
            Button(
                onClick = viewModel::addChild,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading && state.name.isNotBlank() && state.birthDate != null
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Добавить ребёнка")
                }
            }

            // Подсказка о обязательных полях
            Text(
                text = "* - обязательные поля",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Extension функции для конвертации между Date и LocalDate
fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

@Preview
@Composable
fun PreviewAddChildScreen() {
    MaterialTheme {
        // AddChildScreen(navController = rememberNavController())
    }
}
