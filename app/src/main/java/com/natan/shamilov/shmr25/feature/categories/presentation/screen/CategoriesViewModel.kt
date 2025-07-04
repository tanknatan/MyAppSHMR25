package com.natan.shamilov.shmr25.feature.categories.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.categories.domain.entity.Category
import com.natan.shamilov.shmr25.feature.categories.domain.usecase.GetCategoriesListUseCase
import com.natan.shamilov.shmr25.feature.categories.domain.usecase.LoadCategoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
    private val loadCategoriesUseCase: LoadCategoriesListUseCase,
    private val networkStateReceiver: NetworkStateReceiver,
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            networkStateReceiver.isNetworkAvailable.collect { isAvailable ->
                if (isAvailable && _uiState.value == State.Error) {
                    loadCategories()
                }
            }
        }
    }

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
        viewModelScope.launch {
            _uiState.value = State.Loading
            if (getCategoriesListUseCase().isEmpty()) {
                when (val result = loadCategoriesUseCase()) {
                    is Result.Success -> {
                        _categories.value = getCategoriesListUseCase()
                        _uiState.value = State.Content
                    }

                    is Result.Error -> {
                        _uiState.value = State.Error
                        Log.e("CategoriesViewModel", "Ошибка загрузки категорий: ${result.exception.message}")
                    }

                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } else {
                _categories.value = getCategoriesListUseCase()
                _uiState.value = State.Content
            }
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
