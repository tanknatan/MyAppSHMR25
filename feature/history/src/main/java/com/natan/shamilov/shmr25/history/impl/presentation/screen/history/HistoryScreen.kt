package com.natan.shamilov.shmr25.history.impl.presentation.screen.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.HistoryType
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AppCard
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomDatePickerDialog
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ListEmptyScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.history.R
import com.natan.shamilov.shmr25.history.impl.domain.model.HistoryItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(factory = LocalViewModelFactory.current),
    type: HistoryType,
    onBackClick: () -> Unit,
    onItemClick: (HistoryItem) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(type)
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
                LoadingScreen(innerPadding = innerPadding)
            }

            is State.Error -> {
                ErrorScreen(innerPadding = innerPadding) { viewModel.initialize(type) }
            }

            is State.Content -> {
                HistoryContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    onRetry = { viewModel.initialize(type) },
                    onItemClick = { item ->
                        onItemClick(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoryContent(
    paddingValues: PaddingValues,
    viewModel: HistoryViewModel,
    onRetry: () -> Unit,
    onItemClick: (HistoryItem) -> Unit,
) {
    val historyUiModel by viewModel.historyUiModel.collectAsStateWithLifecycle()
    val startDate by viewModel.selectedPeriodStart.collectAsStateWithLifecycle()
    val endDate by viewModel.selectedPeriodEnd.collectAsStateWithLifecycle()

    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val isoFormatter = remember { DateTimeFormatter.ISO_DATE_TIME }

    var showDialog by remember { mutableStateOf(false) }
    var isStartDatePicker by remember { mutableStateOf(true) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

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

        historyUiModel?.let { model ->
            TopGreenCard(
                title = stringResource(R.string.total_amount),
                amount = model.totalAmount
            )
        }

        if (historyUiModel?.items?.isEmpty() == true) {
            ListEmptyScreen(onRetry = {
                onRetry()
            })
        } else {
            LazyColumn {
                historyUiModel?.let { model ->
                    items(
                        items = model.items,
                        key = { "history_${it.id}" }
                    ) { item ->
                        AppCard(
                            title = item.name,
                            amount = item.amount,
                            subAmount = LocalDateTime.parse(item.createdAt, isoFormatter).format(timeFormatter),
                            avatarEmoji = item.emoji,
                            subtitle = item.comment,
                            canNavigate = true,
                            onNavigateClick = { onItemClick(item) },
                            currency = item.currency
                        )
                    }
                }
            }
        }

        if (showDialog) {
            CustomDatePickerDialog(
                initialDate = if (isStartDatePicker) startDate else endDate,
                onDismissRequest = { showDialog = false },
                onClear = {
                    if (isStartDatePicker) {
                        viewModel.clearStartDate()
                    } else {
                        viewModel.clearEndDate()
                    }
                    showDialog = false
                },
                onDateSelected = { selectedDate ->
                    if (isStartDatePicker) {
                        viewModel.updatePeriod(selectedDate!!, endDate)
                    } else {
                        viewModel.updatePeriod(startDate, selectedDate!!)
                    }
                    showDialog = false
                },
            )
        }
    }
}
