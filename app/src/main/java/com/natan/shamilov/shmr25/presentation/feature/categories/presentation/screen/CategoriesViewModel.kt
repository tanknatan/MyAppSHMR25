package com.natan.shamilov.shmr25.presentation.feature.categories.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.data.api.NetworkStateReceiver
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.usecase.GetCategoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
    private val networkStateReceiver: NetworkStateReceiver
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _myCategories = MutableStateFlow<List<Category>>(emptyList())
    val myCategories: StateFlow<List<Category>> = _myCategories.asStateFlow()

    private var dataLoadingJob: Job? = null

    init {
        // Только подписываемся на сеть, но не загружаем данные сразу
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

    private fun loadCategories() {
        // Отменяем предыдущую задачу если она существует
        dataLoadingJob?.cancel()
        dataLoadingJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _uiState.value = State.Loading
                }

                when (val result = getCategoriesListUseCase()) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            _myCategories.value = result.data
                            _uiState.value = State.Content
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            _uiState.value = State.Error
                        }
                    }
                    is Result.Loading -> {
                        withContext(Dispatchers.Main) {
                            _uiState.value = State.Loading
                        }
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) {
                    Log.d("CategoriesViewModel", "Загрузка категорий отменена")
                } else {
                    Log.e("CategoriesViewModel", "Ошибка при загрузке категорий: ${e.message}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CategoriesViewModel", "ViewModel уничтожен, отменяем все задачи")
        dataLoadingJob?.cancel()
    }
}
