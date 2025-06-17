package com.natan.shamilov.shmr25.presentation.screens.incomes

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.domain.usecase.GetIncomesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        getIncomesList()
    }

    private fun getIncomesList() {
        _myIncomes.value = getIncomesListUseCase()
        _sumOfIncomes.value = myIncomes.value.sumOf { it.amount }
    }
}