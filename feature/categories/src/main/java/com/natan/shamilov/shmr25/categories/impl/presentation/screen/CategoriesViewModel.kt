package com.natan.shamilov.shmr25.categories.impl.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.categories.impl.domain.usecase.GetCategoriesListUseCase
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана категорий.
 * Ответственность: Управление данными и состоянием UI для отображения списка категорий,
 * включая загрузку категорий, обработку сетевых ошибок и обновление данных
 * при изменении состояния сети.
 */
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    fun initialize() {
        loadCategories()
    }

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    val filteredCategories: StateFlow<List<Category>> = combine(_categories, _query) { categories, query ->
        if (query.isBlank()) {
            categories
        } else {
            categories.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(FLOW_STOP_TIMEOUT_MS),
        emptyList()
    )

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            val categoriesListResult = getCategoriesListUseCase()
            _categories.value = categoriesListResult
            _uiState.value = State.Content
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CategoriesViewModel", "ViewModel уничтожен, отменяем все задачи")
    }

    companion object {
        private const val FLOW_STOP_TIMEOUT_MS: Long = 5000
    }
}
