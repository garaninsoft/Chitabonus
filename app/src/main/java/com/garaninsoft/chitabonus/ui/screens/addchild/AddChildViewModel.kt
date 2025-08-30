package com.garaninsoft.chitabonus.ui.screens.addchild

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.usecase.AddChildUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class AddChildState(
    val name: String = "",
    val birthDate: Date? = null,
    val avatarUri: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AddChildViewModel @Inject constructor(
    private val addChildUseCase: AddChildUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddChildState())
    val state: StateFlow<AddChildState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun onBirthDateChange(date: Date) {
        _state.update { it.copy(birthDate = date) }
    }

    fun onAvatarChange(uri: String?) {
        _state.update { it.copy(avatarUri = uri) }
    }

    fun addChild() {
        val currentState = _state.value
        if (currentState.name.isBlank()) {
            _state.update { it.copy(error = "Введите имя ребёнка") }
            return
        }

        if (currentState.birthDate == null) {
            _state.update { it.copy(error = "Выберите дату рождения") }
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val child = Child(
                    name = currentState.name,
                    birthDate = currentState.birthDate,
                    avatarUri = currentState.avatarUri,
                    currentBalance = 0.0
                )

                addChildUseCase(child)
                _state.update { it.copy(isLoading = false, isSuccess = true) }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка при добавлении: ${e.message}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}