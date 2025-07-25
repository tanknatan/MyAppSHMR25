package com.natan.shamilov.shmr25.incomes.impl.presentation.screen.addIncomes.addExpenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.natan.shamilov.shmr25.incomes.R
import com.natan.shamilov.shmr25.incomes.impl.presentation.navigation.IncomesFlow
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddIncomesScreen(
    viewModel: AddIncomesViewModel = viewModel(factory = LocalViewModelFactory.current),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                IncomesFlow.AddIncome.startIcone,
                IncomesFlow.AddIncome.title,
                IncomesFlow.AddIncome.endIcone,
                onBackOrCanselClick = {
                    viewModel.vibrate()
                    onBackPressed()
                },
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
                AddIncomesContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun AddIncomesContent(
    paddingValues: PaddingValues,
    viewModel: AddIncomesViewModel,
) {
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedAccount by viewModel.selectedAccount.collectAsStateWithLifecycle(null)
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle(null)
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
                initialTime = viewModel.selectedTime.value ?: LocalTime.now(),
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
