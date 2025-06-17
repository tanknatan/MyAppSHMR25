package com.natan.shamilov.shmr25.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.domain.entity.Category
import com.natan.shamilov.shmr25.domain.usecase.GetCategoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase
): ViewModel() {

    private var _myCategories = MutableStateFlow<List<Category>>(emptyList())
    val myCategories: StateFlow<List<Category>>
        get() = _myCategories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _myCategories.value = getCategoriesListUseCase()
        }
    }
}