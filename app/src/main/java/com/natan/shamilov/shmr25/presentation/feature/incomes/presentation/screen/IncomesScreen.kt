package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.ui.TopGreenCard

@Composable
fun IncomesTodayScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesViewModel = hiltViewModel(),
    onHistoryClick: () -> Unit,
    onFABClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Incomes.startIcone,
                Screen.Incomes.title,
                Screen.Incomes.endIcone,
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
                IncomesContent(
                    viewModel = viewModel,
                    paddingValues = innerPadding
                )
            }
        }
    }
}

@Composable
fun IncomesContent(viewModel: IncomesViewModel, paddingValues: PaddingValues) {
    val total by viewModel.sumOfIncomes.collectAsStateWithLifecycle()
    val myIncomes by viewModel.incomes.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.total),
            amount = total
        )

        LazyColumn {
            items(
                items = myIncomes,
                key = { incomes -> incomes.id }
            ) { incomes ->
                AppCard(
                    title = incomes.category.name,
                    amount = incomes.amount,
                    canNavigate = true,
                    onNavigateClick = {}
                )
            }
        }
    }
}
