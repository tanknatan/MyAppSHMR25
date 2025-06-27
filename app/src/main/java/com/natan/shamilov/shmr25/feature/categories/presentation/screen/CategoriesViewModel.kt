package com.natan.shamilov.shmr25.feature.categories.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.app.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.feature.categories.domain.usecase.GetCategoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private var dataLoadingJob: Job? = null
    private var networkJob: Job? = null

    init {
        networkJob = viewModelScope.launch {
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

    private fun loadCategories() {
        dataLoadingJob?.cancel()
        
        dataLoadingJob = viewModelScope.launch {
            try {
                _uiState.value = State.Loading

                when (val result = getCategoriesListUseCase()) {
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _uiState.value = State.Content // Пустой список категорий - это валидное состояние
                            Log.d("CategoriesViewModel", "Список категорий пуст")
                        } else {
                            _categories.value = result.data
                            _uiState.value = State.Content
                        }
                    }
                    is Result.Error -> {
                        _uiState.value = State.Error
                        Log.e("CategoriesViewModel", "Ошибка загрузки категорий: ${result.exception.message}")
                    }
                    is Result.Loading -> {
                        _uiState.value = State.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                _uiState.value = State.Error
                Log.e("CategoriesViewModel", "Неожиданная ошибка при загрузке категорий", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CategoriesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
        networkJob?.cancel()
    }
}
