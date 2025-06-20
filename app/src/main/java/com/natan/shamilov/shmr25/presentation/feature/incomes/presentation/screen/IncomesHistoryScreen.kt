package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import java.util.Date
import java.util.Locale


@Composable
fun IncomesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesViewModel = hiltViewModel(LocalActivity.current!! as MainActivity),
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.IncomesHistory.startIcone,
                Screen.IncomesHistory.title,
                Screen.IncomesHistory.endIcone,
                onBackOrCanselClick = {onBackPressed()},
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
                    Text(text = "Error") // Пока так
                }
            }

            is State.Content -> {

                IncomesHistoryContent(
                    innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun IncomesHistoryContent(
    paddingValues: PaddingValues,
    viewModel: IncomesViewModel,
) {
    val total by viewModel.sumOfIncomes.collectAsStateWithLifecycle()
    val myIncomes by viewModel.myIncomes.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var currentPicker by remember { mutableStateOf(DateType.START) }

    var startDateMillis by remember { mutableStateOf<Long?>(null) }
    var endDateMillis by remember { mutableStateOf<Long?>(null) }

    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    val formattedStartDate = startDateMillis?.let { dateFormatter.format(Date(it)) } ?: "Выберите дату"
    val formattedEndDate = endDateMillis?.let { dateFormatter.format(Date(it)) } ?: "Выберите дату"

    Column(modifier = Modifier.padding(paddingValues)) {

        // Карточка начала периода
        TopGreenCard(
            title = stringResource(R.string.start_date),
            cucurrency = formattedStartDate,
            onNavigateClick = {
                currentPicker = DateType.START
                showDialog = true
            }
        )

        // Карточка конца периода
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

        // Список операций
        LazyColumn {
            items(myIncomes, key = { it.id }) { income ->
                AppCard(
                    title = income.category.name,
                    amount = income.amount,
                    subAmount = "Октябрь 2022", // можно заменить на дату income
                    avatarEmoji = income.category.emoji,
                    subtitle = income.comment,
                    canNavigate = true,
                    onNavigateClick = {},
                )
            }
        }

        // Диалог выбора даты
        if (showDialog) {
            CustomDatePickerDialog(
                initialDate = when (currentPicker) {
                    DateType.START -> startDateMillis
                    DateType.END -> endDateMillis
                },
                onDismissRequest = { showDialog = false },
                onClear = {
                    when (currentPicker) {
                        DateType.START -> startDateMillis = null
                        DateType.END -> endDateMillis = null
                    }
                },
                onDateSelected = {
                    when (currentPicker) {
                        DateType.START -> startDateMillis = it
                        DateType.END -> endDateMillis = it
                    }
                }
            )
        }
    }
}

// enum для выбора типа даты
enum class DateType {
    START, END
}


//@Composable
//fun IncomesHistoryContent(
//    paddingValues: PaddingValues,
//    viewModel: IncomesViewModel,
//) {
//    val total by viewModel.sumOfIncomes.collectAsStateWithLifecycle()
//    val myIncomes by viewModel.myIncomes.collectAsStateWithLifecycle()
//
//    Column(modifier = Modifier.padding(paddingValues)) {
//        TopGreenCard(
//            title = stringResource(R.string.start_date),
//            amount = total,
//            onNavigateClick = {}
//
//        )
//        TopGreenCard(
//            title = stringResource(R.string.end_date),
//            amount = total,
//            onNavigateClick = {}
//        )
//        TopGreenCard(
//            title = stringResource(R.string.sum),
//            amount = total
//        )
//
//        LazyColumn {
//            items(
//                items = myIncomes,
//                key = { income -> income.id }) { income ->
//                AppCard(
//                    title = income.category.name,
//                    amount = income.amount,
//                    subAmount = "Октябрь 2022",
//                    avatarEmoji = income.category.emoji,
//                    subtitle = income.comment,
//                    canNavigate = true,
//                    onNavigateClick = {},
//                )
//            }
//        }
//    }
//}
