package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Income
import com.natan.shamilov.shmr25.domain.usecase.CheckAccAndTransactionDataLoadingUseCase
import com.natan.shamilov.shmr25.domain.usecase.GetIncomesListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadIncomesByPeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val getIncomesListUseCase: GetIncomesListUseCase,
    private val checkAccAndTransactionDataLoadingUseCase: CheckAccAndTransactionDataLoadingUseCase,
    private val loadIncomesByPeriodUseCase: LoadIncomesByPeriodUseCase,
) : ViewModel() {

    private val _myIncomes = MutableStateFlow<List<Income>>(emptyList())
    val myIncomes: StateFlow<List<Income>>
        get() = _myIncomes

    private val _myIncomesByPeriod = MutableStateFlow<List<Income>>(emptyList())
    val myIncomesByPeriod: StateFlow<List<Income>>
        get() = _myIncomesByPeriod

    private val _sumOfIncomes = MutableStateFlow<Double>(0.0)
    val sumOfIncomes: StateFlow<Double>
        get() = _sumOfIncomes

    private val _sumOfIncomesByPeriod = MutableStateFlow<Double>(0.0)
    val sumOfIncomesByPeriod: StateFlow<Double>
        get() = _sumOfIncomesByPeriod

    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        getIncomesList()
    }

    private fun getIncomesList() {
        viewModelScope.launch {
            checkAccAndTransactionDataLoadingUseCase().collect {
                _uiState.value = it
                when (it) {
                    State.Content -> {
                        _myIncomes.value = getIncomesListUseCase()
                        _sumOfIncomes.value = myIncomes.value.sumOf { it.amount }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun loadIncomesByPeriod(
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadIncomesByPeriodUseCase(
                startDate = startDate,
                endDate = endDate
            )
            when (result) {
                is Result.Error -> {
                    _uiState.value = State.Error
                }

                Result.Loading -> {
                    _uiState.value = State.Loading
                }
                is Result.Success<List<Income>> -> {

                    _myIncomesByPeriod.value = result.data
                    Log.d("IncomesViewModel",startDate )
                    Log.d("IncomesViewModel",endDate )
                    _sumOfIncomesByPeriod.value = myIncomesByPeriod.value.sumOf { it.amount }
                }
            }
        }

    }
}