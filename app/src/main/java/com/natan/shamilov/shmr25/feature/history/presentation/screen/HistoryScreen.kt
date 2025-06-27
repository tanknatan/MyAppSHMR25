package com.natan.shamilov.shmr25.feature.history.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.feature.history.domain.HistoryType
import com.natan.shamilov.shmr25.common.ui.AppCard
import com.natan.shamilov.shmr25.common.ui.CustomDatePickerDialog
import com.natan.shamilov.shmr25.common.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.ui.TopGreenCard
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    type: HistoryType,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                startIcone = R.drawable.ic_back,
                title = when (type) {
                    HistoryType.EXPENSE -> R.string.expenses_history
                    HistoryType.INCOME -> R.string.incomes_history
                },
                endIcone = R.drawable.ic_analytics,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                }
            }

            is State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.no_network))
                }
            }

            is State.Content -> {
                HistoryContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    type = type
                )
            }
        }
    }
}

@Composable
private fun HistoryContent(
    paddingValues: PaddingValues,
    viewModel: HistoryViewModel,
    type: HistoryType
) {
    val historyData by viewModel.historyData.collectAsStateWithLifecycle()
    val startDate by viewModel.selectedPeriodStart.collectAsStateWithLifecycle()
    val endDate by viewModel.selectedPeriodEnd.collectAsStateWithLifecycle()
    
    var showDialog by remember { mutableStateOf(false) }
    var isStartDatePicker by remember { mutableStateOf(true) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val isoFormatter = DateTimeFormatter.ISO_DATE_TIME

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.start_date),
            cucurrency = Instant.ofEpochMilli(startDate)
                .atZone(ZoneId.systemDefault())
                .format(dateFormatter),
            onNavigateClick = {
                isStartDatePicker = true
                showDialog = true
            }
        )

        TopGreenCard(
            title = stringResource(R.string.end_date),
            cucurrency = Instant.ofEpochMilli(endDate)
                .atZone(ZoneId.systemDefault())
                .format(dateFormatter),
            onNavigateClick = {
                isStartDatePicker = false
                showDialog = true
            }
        )

        historyData?.let { data ->
            // Показываем соответствующую сумму в зависимости от типа
            TopGreenCard(
                title = stringResource(
                    when (type) {
                        HistoryType.EXPENSE -> R.string.total_expenses
                        HistoryType.INCOME -> R.string.total_incomes
                    }
                ),
                amount = when (type) {
                    HistoryType.EXPENSE -> data.totalExpenses
                    HistoryType.INCOME -> data.totalIncomes
                }
            )

            LazyColumn {
                // Показываем только соответствующие транзакции в зависимости от типа
                when (type) {
                    HistoryType.EXPENSE -> {
                        items(
                            items = data.expenses,
                            key = { "expense_${it.id}" }
                        ) { expense ->
                            AppCard(
                                title = expense.category.name,
                                amount = -expense.amount,
                                subAmount = LocalDateTime.parse(expense.createdAt, isoFormatter).format(timeFormatter),
                                avatarEmoji = expense.category.emoji,
                                subtitle = expense.comment,
                                canNavigate = true,
                                onNavigateClick = {}
                            )
                        }
                    }
                    HistoryType.INCOME -> {
                        items(
                            items = data.incomes,
                            key = { "income_${it.id}" }
                        ) { income ->
                            AppCard(
                                title = income.category.name,
                                amount = income.amount,
                                subAmount = LocalDateTime.parse(income.createdAt, isoFormatter).format(timeFormatter),
                                avatarEmoji = income.category.emoji,
                                subtitle = income.comment,
                                canNavigate = true,
                                onNavigateClick = {}
                            )
                        }
                    }
                }
            }
        }

        if (showDialog) {
            CustomDatePickerDialog(
                initialDate = if (isStartDatePicker) startDate else endDate,
                onDismissRequest = { showDialog = false },
                onClear = {},
                onDateSelected = { selectedDate ->
                    if (isStartDatePicker) {
                        viewModel.updatePeriod(selectedDate!!, endDate)
                    } else {
                        viewModel.updatePeriod(startDate, selectedDate!!)
                    }
                    showDialog = false
                }
            )
        }
    }
}
