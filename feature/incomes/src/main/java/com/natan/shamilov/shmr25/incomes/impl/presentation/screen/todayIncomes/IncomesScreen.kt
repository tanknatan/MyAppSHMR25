package com.natan.shamilov.shmr25.incomes.impl.presentation.screen.todayIncomes

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
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import com.natan.shamilov.shmr25.incomes.R
import com.natan.shamilov.shmr25.incomes.impl.presentation.navigation.IncomesFlow

@Composable
fun IncomesTodayScreen(
    viewModel: IncomesViewModel = viewModel(factory = LocalViewModelFactory.current),
    onHistoryClick: () -> Unit,
    onFABClick: () -> Unit,
    onItemClick: (Transaction) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                IncomesFlow.IncomesToday.startIcone,
                IncomesFlow.IncomesToday.title,
                IncomesFlow.IncomesToday.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = {
                    viewModel.vibrate()
                    onHistoryClick()
                }
            )
        },
        floatingActionButton = {
            MyFloatingActionButton({
                viewModel.vibrate()
                onFABClick()
            })
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
                val total by viewModel.sumOfIncomes.collectAsStateWithLifecycle()
                val myIncomes by viewModel.incomes.collectAsStateWithLifecycle()
                IncomesContent(
                    paddingValues = innerPadding,
                    total = total,
                    myIncomes = myIncomes,
                    onRetry = {
                        viewModel.vibrate()
                        viewModel.initialize() },
                    onItemClick = { income ->
                        onItemClick(income)
                    },
                )
            }
        }
    }
}

@Composable
fun IncomesContent(
    paddingValues: PaddingValues,
    total: Double,
    myIncomes: List<Transaction>,
    onRetry: () -> Unit,
    onItemClick: (Transaction) -> Unit,
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = localizedString(R.string.total),
            amount = total
        )
        if (myIncomes.isEmpty()) {
            ListEmptyScreen(onRetry = {
                onRetry()
            })
        } else {
            LazyColumn {
                items(
                    items = myIncomes,
                    key = { incomes -> incomes.id }
                ) { incomes ->
                    AppCard(
                        title = incomes.name,
                        amount = incomes.amount,
                        canNavigate = true,
                        onNavigateClick = { onItemClick(incomes) },
                        currency = incomes.currency
                    )
                }
            }
        }
    }
}
