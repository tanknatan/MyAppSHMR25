package com.natan.shamilov.shmr25.expenses.impl.presentation.screen.editExpenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AccountDropdownMenu
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AmountInputField
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AppCard
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CategoriesDropdownMenu
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomDatePickerDialog
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTimePickerDialog
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.SingleLineTextField
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import com.natan.shamilov.shmr25.common.impl.presentation.utils.convertCurrency
import com.natan.shamilov.shmr25.expenses.R
import com.natan.shamilov.shmr25.expenses.impl.presentation.navigation.ExpensesFlow
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EditExpensesScreen(
    expenseId: String,
    viewModel: EditExpensesViewModel = viewModel(factory = LocalViewModelFactory.current),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(expenseId) {
        viewModel.initialize(expenseId)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                ExpensesFlow.EditExpense.startIcone,
                ExpensesFlow.EditExpense.title,
                ExpensesFlow.EditExpense.endIcone,
                onBackOrCanselClick = {
                    viewModel.vibrate()
                    onBackPressed() },
                onNavigateClick = {
                    viewModel.vibrate()
                    if (viewModel.isFormValidNow()) {
                        viewModel.createTransaction(onSuccess = { onBackPressed() })
                    }
                },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is State.Loading -> {
                LoadingScreen(innerPadding = innerPadding)
            }

            is State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = localizedString(R.string.not_network))
                }
            }

            is State.Content -> {
                EditExpensesContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    onBackPressed = {
                        onBackPressed()
                    },
                )
            }
        }
    }
}

@Composable
fun EditExpensesContent(
    paddingValues: PaddingValues,
    viewModel: EditExpensesViewModel,
    onBackPressed: () -> Unit,
) {
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedAccount by viewModel.selectedAccount.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val amount by viewModel.amount.collectAsStateWithLifecycle()
    val comment by viewModel.comment.collectAsStateWithLifecycle()

    val date by viewModel.selectedDate.collectAsStateWithLifecycle()
    val time by viewModel.selectedTime.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm")) ?: localizedString(R.string.select)
    Column(modifier = Modifier.padding(paddingValues)) {
        AccountDropdownMenu(
            accounts = accounts,
            selectedAccount = selectedAccount,
            onAccountSelected = {

                viewModel.selectAccount(it)
            }
        )
        CategoriesDropdownMenu(
            categories = categories,
            selectedCategory = selectedCategory,
            onAccountSelected = {
                viewModel.selectCategory(it)
            }
        )
        AmountInputField(
            amount = amount,
            onAmountChange = {
                viewModel.updateAmount(it)
            },
            currency = selectedAccount?.currency?.convertCurrency(),
        )
        AppCard(
            title = localizedString(R.string.date),
            onNavigateClick = {
                showDialog = true
            },
            stringDate = Instant.ofEpochMilli(date)
                .atZone(ZoneId.systemDefault())
                .format(dateFormatter),
        )

        AppCard(
            title = localizedString(R.string.time),
            stringDate = formattedTime,
            onNavigateClick = { showTimeDialog = true }
        )
        SingleLineTextField(
            value = comment,
            onValueChange = { viewModel.updateText(it) },
            placeholder = localizedString(R.string.comment)
        )
        Spacer(modifier = Modifier.height(16.dp))
//        CustomButton(
//            onButtonClick = { viewModel.deleteTransaction(onSuccess = { onBackPressed() }) },
//            text = "Удалить транзакцию",
//            isEnabled = true,
//            color = Color.Red
//        )

        if (showDialog) {
            CustomDatePickerDialog(
                initialDate = date,
                onDismissRequest = { showDialog = false },
                onClear = {
                    viewModel.clearEndDate()
                },
                onDateSelected = { selectedDate ->
                    viewModel.updateDate(selectedDate)
                    showDialog = false
                },
            )
        }
        if (showTimeDialog) {
            CustomTimePickerDialog(
                initialTime = time,
                onDismissRequest = { showTimeDialog = false },
                onClear = {
                    viewModel.clearTime()
                    showTimeDialog = false
                },
                onTimeSelected = { selected ->
                    viewModel.updateTime(selected)
                    showTimeDialog = false
                }
            )
        }
    }
}