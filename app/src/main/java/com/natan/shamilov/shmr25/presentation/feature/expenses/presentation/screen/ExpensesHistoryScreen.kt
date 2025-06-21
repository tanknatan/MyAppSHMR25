package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import androidx.activity.compose.LocalActivity
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.presentation.MainActivity
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.CustomDatePickerDialog
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.TopGreenCard
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun ExpensesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(LocalActivity.current!! as MainActivity),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.ExpensesHistory.startIcone,
                Screen.ExpensesHistory.title,
                Screen.ExpensesHistory.endIcone,
                onBackOrCanselClick = {onBackClick()},
                onNavigateClick = { },
            )
        },
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

                ExpensesHistoryContent(
                    innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}


@Composable
fun ExpensesHistoryContent(
    paddingValues: PaddingValues,
    viewModel: ExpensesViewModel,
) {
    val total by viewModel.sumOfExpenses.collectAsStateWithLifecycle()
    val myExpenses by viewModel.myExpensesByPeriod.collectAsStateWithLifecycle()

    var startDateMillis by remember {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        mutableLongStateOf(calendar.timeInMillis)
    }

    var endDateMillis by remember {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        mutableStateOf(calendar.timeInMillis)
    }

    var showDialog by remember { mutableStateOf(false) }
    var currentPicker by remember { mutableStateOf(DateType.START) }

    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val formattedStartDate = startDateMillis?.let { dateFormatter.format(Date(it)) } ?: "Выберите дату"
    val formattedEndDate = endDateMillis?.let { dateFormatter.format(Date(it)) }
        ?: "Выберите дату"


    LaunchedEffect(startDateMillis, endDateMillis) {
        viewModel.loadExpensesByPeriod(
            startDate = formattedStartDate,
            endDate = formattedEndDate
        )
    }

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
                items = myExpenses,
                key = { it.id }
            ) { expense ->
                AppCard(
                    title = expense.category.name,
                    amount = expense.amount,
                    subAmount = expense.createdAt,
                    avatarEmoji = expense.category.emoji,
                    subtitle = expense.comment,
                    canNavigate = true,
                    onNavigateClick = {},
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
                onClear = {

                },
                onDateSelected = {
                    when (currentPicker) {
                        DateType.START -> startDateMillis = it!!
                        DateType.END -> endDateMillis = it!!
                    }
                }
            )
        }
    }
}

enum class DateType {
    START, END
}

