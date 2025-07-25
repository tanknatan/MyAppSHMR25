package com.natan.shamilov.shmr25.history.impl.presentation.screen.editHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.presentation.BaseViewModel
import com.natan.shamilov.shmr25.common.impl.presentation.utils.formatDateToMillis
import com.natan.shamilov.shmr25.common.impl.presentation.utils.toLocalTimeWithoutSeconds
import com.natan.shamilov.shmr25.common.impl.presentation.utils.toUtcIsoString
import com.natan.shamilov.shmr25.history.impl.domain.usecase.DeletTransactionUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.EditTransactionUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetAccountByIdUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetAccountUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetCategoriesListUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetCategoryByIdUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetSelectedAccountUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.GetTransacrionByIdUseCase
import com.natan.shamilov.shmr25.history.impl.domain.usecase.SetSelectedAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class EditHistoryViewModel @Inject constructor(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getSelectedAccountUseCase: GetSelectedAccountUseCase,
    private val setSelectedAccountUseCase: SetSelectedAccountUseCase,
    private val getTransacrionByIdUseCase: GetTransacrionByIdUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val editTransactionUseCase: EditTransactionUseCase,
    private val deleteTransactionUseCase: DeletTransactionUseCase,
    private val hapticProvider: HapticProvider
) : BaseViewModel(hapticProvider) {
    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _expense = MutableStateFlow<Transaction?>(null)
    val expense: StateFlow<Transaction?> = _expense.asStateFlow()

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _selectedDate = MutableStateFlow(
        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow(LocalTime.now())
    val selectedTime: StateFlow<LocalTime> = _selectedTime.asStateFlow()

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment.asStateFlow()

    fun updateText(newText: String) {
        _comment.value = newText
    }

    fun isFormValidNow(): Boolean {
        return selectedAccount.value != null &&
                selectedCategory.value != null &&
                amount.value.isNotBlank()
    }

    fun updateTime(time: LocalTime) {
        _selectedTime.value = time
    }

    fun clearTime() {
        _selectedTime.value = LocalTime.now()
    }

    fun clearEndDate() {
        val now = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        _selectedDate.value = now
    }

    fun updateDate(date: Long?) {
        if (date != null) {
            _selectedDate.value = date
        }
    }

    fun updateAmount(input: String) {
        val normalizedInput = input.replace(',', '.')
        val parsedAmount = normalizedInput.toDoubleOrNull()
        if (parsedAmount != null) {
            _amount.value = normalizedInput
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            setSelectedAccountUseCase(account.id)
            _selectedAccount.value = getSelectedAccountUseCase()
        }
    }

    fun selectCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_categories.value.any { it.id == category.id }) {
                _selectedCategory.value = category
            }
        }
    }

    fun initialize(transactionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("EditHistoryVM", "transactionId: $transactionId")
            when (val expense = getTransacrionByIdUseCase(transactionId.toInt())) {
                is Result.Success<Transaction> -> {
                    loadCategories(expense.data.isIncome)
                    val accounts = getAccountUseCase()

                    // Обновляем всё
                    _expense.value = expense.data
                    Log.d("EditExpensesVM", "expense = $expense")
                    _accounts.value = accounts
                    Log.d("EditExpensesVM", "categories = ${categories.value}")
                    Log.d("EditExpensesVM", "accounts = $accounts")

                    _amount.value = (expense.data.amount ?: "").toString()
                    _comment.value = expense.data.comment.orEmpty()

                    _selectedCategory.value = getCategoryByIdUseCase(expense.data.categoryId, expense.data.isIncome)
                    _selectedAccount.value = getAccountByIdUseCase(expense.data.accountId)

                    Log.d("EditExpensesVM", "selectedCategory = ${_selectedCategory.value}")

                    expense.data.createdAt.let { createdAt ->
                        _selectedDate.value = createdAt.formatDateToMillis()
                        _selectedTime.value = createdAt.toLocalTimeWithoutSeconds()
                        Log.d("EditExpensesVM", "parsed createdAt = $createdAt")
                    }
                    _uiState.value = State.Content
                }

                is Result.Error -> TODO()
                Result.Loading -> TODO()
            }
        }
    }

    fun deleteTransaction(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.value = State.Loading
            when (deleteTransactionUseCase(_expense.value!!.id.toInt())) {
                is Result.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                }

                is Result.Error -> {
                    _uiState.value = State.Error
                }

                is Result.Loading -> {
                    _uiState.value = State.Loading
                }
            }
        }
    }

    fun createTransaction(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            when (
                editTransactionUseCase(
                    transactionId = _expense.value!!.id.toInt(),
                    accountId = _selectedAccount.value!!.id,
                    categoryId = _selectedCategory.value!!.id.toInt(),
                    amount = amount.value,
                    transactionDate = toUtcIsoString(
                        dateMillis = _selectedDate.value,
                        time = _selectedTime.value
                    ),
                    comment = comment.value
                )
            ) {
                is Result.Error -> _uiState.value = State.Error
                Result.Loading -> _uiState.value = State.Loading
                is Result.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                }
            }
        }
    }

    private suspend fun loadCategories(isIncomes : Boolean) {
        _categories.value = getCategoriesListUseCase(isIncomes)
    }
}
