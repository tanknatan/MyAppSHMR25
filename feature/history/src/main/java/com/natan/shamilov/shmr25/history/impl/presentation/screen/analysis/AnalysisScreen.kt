package com.natan.shamilov.shmr25.history.impl.presentation.screen.analysis

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
import androidx.compose.ui.graphics.Color
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
import com.natan.shamilov.shmr25.history.R
import com.natan.shamilov.shmr25.history.impl.presentation.navigation.HistoryFlow
import com.natan.shamilov.shmr25.history.impl.presentation.utils.generateColorsHSV
import com.natan.shamilov.shmr25.history.impl.presentation.utils.toPieChartData
import com.natan.shamilov.shmr25.schedule.AnalysisSchedule
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AnalysisScreen(
    type: HistoryType,
    viewModel: AnalysisViewModel = viewModel(factory = LocalViewModelFactory.current),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(type)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                startIcone = HistoryFlow.Analysis.startIcone,
                title = HistoryFlow.Analysis.title,
                endIcone = HistoryFlow.Analysis.endIcone,
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
                AnalysisContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    onRetry = { viewModel.initialize(type) },
                )
            }
        }
    }
}

@Composable
private fun AnalysisContent(
    paddingValues: PaddingValues,
    viewModel: AnalysisViewModel,
    onRetry: () -> Unit,
) {
    val historyUiModel by viewModel.analyticsUiModel.collectAsStateWithLifecycle()
    val startDate by viewModel.selectedPeriodStart.collectAsStateWithLifecycle()
    val endDate by viewModel.selectedPeriodEnd.collectAsStateWithLifecycle()
    val colors = historyUiModel?.categoryStats?.let { generateColorsHSV(it.size) }
    var showDialog by remember { mutableStateOf(false) }
    var isStartDatePicker by remember { mutableStateOf(true) }

    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale("ru"))

    Column(modifier = Modifier.padding(paddingValues)) {
        AppCard(
            title = stringResource(R.string.start_date),
            date = Instant.ofEpochMilli(startDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(formatter),
            onNavigateClick = {
                isStartDatePicker = true
                showDialog = true
            },
        )
        AppCard(
            title = stringResource(R.string.end_date),
            date = Instant.ofEpochMilli(endDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(formatter),
            onNavigateClick = {
                isStartDatePicker = false
                showDialog = true
            }
        )
        AppCard(
            title = stringResource(R.string.total_amount),
            amount = historyUiModel?.totalAmount
        )
        AnalysisSchedule(
            data = historyUiModel?.toPieChartData() ?: mapOf("Отсутствуют" to 100f),
            colors = colors ?: listOf(Color.Gray),
        )

        if (historyUiModel?.categoryStats?.isEmpty() == true) {
            ListEmptyScreen(onRetry = {
                onRetry()
            })
        } else {
            LazyColumn {
                historyUiModel?.let { model ->
                    items(
                        items = model.categoryStats,
                    ) { item ->
                        AppCard(
                            title = item.categoryName,
                            avatarEmoji = item.emoji,
                            percent = item.percent,
                            subPercent = item.amount,
                            color = colors?.getOrNull(model.categoryStats.indexOf(item)),
                            //currency = item.currency
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