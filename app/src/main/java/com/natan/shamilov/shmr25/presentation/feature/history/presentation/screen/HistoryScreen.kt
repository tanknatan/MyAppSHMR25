package com.natan.shamilov.shmr25.presentation.feature.history.presentation.screen

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
import com.natan.shamilov.shmr25.presentation.feature.history.domain.DateType
import com.natan.shamilov.shmr25.presentation.feature.history.domain.HistoryType
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.CustomDatePickerDialog
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.TopGreenCard
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    type: HistoryType,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(type) {
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
                    Text(text = "Нет сети")
                }
            }

            is State.Content -> {
                HistoryContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private fun HistoryContent(
    paddingValues: PaddingValues,
    viewModel: HistoryViewModel
) {
    val total by viewModel.sumOfItems.collectAsStateWithLifecycle()
    val items by viewModel.historyItems.collectAsStateWithLifecycle()
    val startDateMillis by viewModel.startDateMillis.collectAsStateWithLifecycle()
    val endDateMillis by viewModel.endDateMillis.collectAsStateWithLifecycle()
    val formattedStartDate = viewModel.formattedStartDate
    val formattedEndDate = viewModel.formattedEndDate

    var showDialog by remember { mutableStateOf(false) }
    var currentPicker by remember { mutableStateOf(DateType.START) }

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.start_date),
            cucurrency = formattedStartDate,
            onNavigateClick = {
                currentPicker = DateType.START
                showDialog = true
            }
        )

        TopGreenCard(
            title = stringResource(R.string.end_date),
            cucurrency = formattedEndDate,
            onNavigateClick = {
                currentPicker = DateType.END
                showDialog = true
            }
        )

        TopGreenCard(
            title = stringResource(R.string.sum),
            amount = total
        )

        LazyColumn {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                AppCard(
                    title = item.category.name,
                    amount = item.amount,
                    subAmount = OffsetDateTime.parse(item.createdAt)
                        .toLocalTime()
                        .format(DateTimeFormatter.ofPattern("H:mm")),
                    avatarEmoji = item.category.emoji,
                    subtitle = item.comment,
                    canNavigate = true,
                    onNavigateClick = {}
                )
            }
        }

        if (showDialog) {
            CustomDatePickerDialog(
                initialDate = when (currentPicker) {
                    DateType.START -> startDateMillis
                    DateType.END -> endDateMillis
                },
                onDismissRequest = { showDialog = false },
                onClear = {},
                onDateSelected = {
                    when (currentPicker) {
                        DateType.START -> viewModel.updateStartDate(it!!)
                        DateType.END -> viewModel.updateEndDate(it!!)
                    }
                    showDialog = false
                }
            )
        }
    }
}
