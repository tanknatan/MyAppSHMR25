package com.natan.shamilov.shmr25.presentation.feature.categories.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.usecase.GetCategoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.natan.shamilov.shmr25.data.api.Result

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
) : ViewModel() {

    private var _myCategories = MutableStateFlow<List<Category>>(emptyList())
    val myCategories: StateFlow<List<Category>>
        get() = _myCategories

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()


    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = State.Loading

            when (val result = getCategoriesListUseCase()) {
                is Result.Success -> {
                    _myCategories.value = result.data
                    _uiState.value = State.Content
                }
                is Result.Error -> {
                    _uiState.value = State.Error
                }
                is Result.Loading -> {
                    _uiState.value =  State.Loading
                }
            }
        }
    }

}