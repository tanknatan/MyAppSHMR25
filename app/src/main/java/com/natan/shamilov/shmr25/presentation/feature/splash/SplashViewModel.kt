package com.natan.shamilov.shmr25.presentation.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.usecase.LoadAccountsListUseCase
import com.natan.shamilov.shmr25.domain.usecase.LoadTodayTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadAccountsListUseCase: LoadAccountsListUseCase,
    private val loadTodayTransactionsUseCase: LoadTodayTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            when (loadAccountsListUseCase()) {
                is Result.Success -> {
                    loadTodayTransactionsUseCase()
                }
                is Result.Error -> {
                }

                is Result.Loading -> {
                }
            }
        }
    }
}