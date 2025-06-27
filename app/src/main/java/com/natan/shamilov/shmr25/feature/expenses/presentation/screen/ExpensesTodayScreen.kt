package com.natan.shamilov.shmr25.feature.expenses.presentation.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.app.navigation.Screen
import com.natan.shamilov.shmr25.common.ui.AppCard
import com.natan.shamilov.shmr25.common.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.ui.TopGreenCard

@Composable
fun ExpensesTodayScreen(
    viewModel: ExpensesViewModel = hiltViewModel(),
    onHistoryClick: () -> Unit,
    onFABClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Инициализируем ViewModel при первом показе экрана
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Expenses.startIcone,
                Screen.Expenses.title,
                Screen.Expenses.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { onHistoryClick() }
            )
        },
        floatingActionButton = {
            MyFloatingActionButton({ onFABClick() })
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
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Box(

                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Нет сети")
                    }
                    TextButton(onClick = { viewModel.loadDataInBackground() }) { Text(text = "Retry") }
                }
            }

            is State.Content -> {
                ExpensesTodayContent(
                    innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ExpensesTodayContent(
    paddingValues: PaddingValues,
    viewModel: ExpensesViewModel,
) {
    val total by viewModel.sumOfExpenses.collectAsStateWithLifecycle()
    val myExpenses by viewModel.expenses.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.total),
            amount = total
        )

        LazyColumn {
            items(
                items = myExpenses,
                key = { expense -> expense.id }
            ) { expense ->
                AppCard(
                    title = expense.category.name,
                    amount = expense.amount,
                    avatarEmoji = expense.category.emoji,
                    subtitle = expense.comment,
                    canNavigate = true,
                    onNavigateClick = {}
                )
            }
        }
    }
}
