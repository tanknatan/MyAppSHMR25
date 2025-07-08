package com.natan.shamilov.shmr25.feature.expenses.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.app.presentation.navigation.Screen
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.common.presentation.ui.AppCard
import com.natan.shamilov.shmr25.common.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.presentation.ui.ListEmptyScreen
import com.natan.shamilov.shmr25.common.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.presentation.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.feature.expenses.domain.entity.Expense

@Composable
fun ExpensesTodayScreen(
    viewModel: ExpensesViewModel = hiltViewModel(),
    onHistoryClick: () -> Unit,
    onFABClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                LoadingScreen(innerPadding = innerPadding)
            }

            is State.Error -> {
                ErrorScreen(innerPadding = innerPadding) { viewModel.initialize() }
            }

            is State.Content -> {
                val total by viewModel.sumOfExpenses.collectAsStateWithLifecycle()
                val myExpenses by viewModel.expenses.collectAsStateWithLifecycle()
                ExpensesTodayContent(
                    paddingValues = innerPadding,
                    total = total,
                    myExpenses = myExpenses,
                    onRetry = { viewModel.initialize() }
                )
            }
        }
    }
}

@Composable
fun ExpensesTodayContent(
    paddingValues: PaddingValues,
    total: Double,
    myExpenses: List<Expense>,
    onRetry: () -> Unit,
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.total),
            amount = total
        )
        if (myExpenses.isEmpty()) {
            ListEmptyScreen(onRetry = {
                onRetry()
            })
        } else {
            LazyColumn {
                items(
                    items = myExpenses,
                    key = { expense -> expense.id }
                ) { expense ->
                    AppCard(
                        title = expense.name,
                        amount = expense.amount,
                        avatarEmoji = expense.emoji,
                        subtitle = expense.comment,
                        canNavigate = true,
                        onNavigateClick = {},
                        currency = expense.currency
                    )
                }
            }
        }
    }
}
