package com.garaninsoft.chitabonus.ui.screens.childlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.domain.usecase.GetChildrenUseCase
import com.garaninsoft.chitabonus.domain.usecase.settings.GetPointsPerUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChildListState(
    val children: List<Child> = emptyList(),
    val pointsPerUnit: Double = 1.0,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val getChildrenUseCase: GetChildrenUseCase,
    private val getPointsPerUnitUseCase: GetPointsPerUnitUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChildListState())
    val state: StateFlow<ChildListState> = _state.asStateFlow()

    init {
        loadChildren()
        loadSettings()
    }

    private fun loadChildren() {
        getChildrenUseCase()
            .onEach { children ->
                _state.update { it.copy(children = children, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val pointsRate = getPointsPerUnitUseCase()
                _state.update { it.copy(pointsPerUnit = pointsRate) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Ошибка загрузки настроек") }
            }
        }
    }

    fun calculateMoneyBalance(points: Double): Double {
        return points / state.value.pointsPerUnit
    }

    fun refresh() {
        _state.update { it.copy(isLoading = true) }
        loadChildren()
        loadSettings()
    }

    // Добавляем метод для очистки ошибки
    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}