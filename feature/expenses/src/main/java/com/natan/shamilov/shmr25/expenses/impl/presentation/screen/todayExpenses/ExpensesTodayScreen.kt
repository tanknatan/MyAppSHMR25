package com.natan.shamilov.shmr25.expenses.impl.presentation.screen.todayExpenses

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.domain.entity.Transaction
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AppCard
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ListEmptyScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.impl.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.expenses.R
import com.natan.shamilov.shmr25.expenses.impl.presentation.navigation.ExpensesFlow

@Composable
fun ExpensesTodayScreen(
    viewModel: ExpensesViewModel = viewModel(factory = LocalViewModelFactory.current),
    onHistoryClick: () -> Unit,
    onFABClick: () -> Unit,
    onItemClick: (Transaction) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                ExpensesFlow.ExpensesToday.startIcone,
                ExpensesFlow.ExpensesToday.title,
                ExpensesFlow.ExpensesToday.endIcone,
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
                    onRetry = { viewModel.initialize() },
                    onItemClick = { expense ->
                        onItemClick(expense)
                    }
                )
            }
        }
    }
}

@Composable
fun ExpensesTodayContent(
    paddingValues: PaddingValues,
    total: Double,
    myExpenses: List<Transaction>,
    onRetry: () -> Unit,
    onItemClick: (Transaction) -> Unit,
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
                        onNavigateClick = { onItemClick(expense) },
                        currency = expense.currency
                    )
                }
            }
        }
    }
}
