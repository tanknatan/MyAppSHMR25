package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.domain.usecase.GetIncomesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val getIncomesListUseCase: GetIncomesListUseCase
): ViewModel() {

    private val _myIncomes = MutableStateFlow<List<Income>>(emptyList())
    val myIncomes: StateFlow<List<Income>>
        get() = _myIncomes

    private val _sumOfIncomes = MutableStateFlow<Int>(0)
    val sumOfIncomes: StateFlow<Int>
        get() = _sumOfIncomes

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()


    init {
        getIncomesList()
    }

    private fun getIncomesList() {
        viewModelScope.launch {
            _myIncomes.value = getIncomesListUseCase()
            _sumOfIncomes.value = myIncomes.value.sumOf { it.amount }
            _uiState.value = State.Content
        }
    }
}