package com.natan.shamilov.shmr25.presentation.feature.expenses.presentation.screen

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.commo.State
import com.natan.shamilov.shmr25.presentation.MainActivity
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.TopGreenCard


@Composable
fun ExpensesHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(LocalActivity.current!! as MainActivity),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Expenses.startIcone,
                Screen.Expenses.title,
                Screen.Expenses.endIcone,
                onBackOrCanselClick = {},
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
    val myExpenses by viewModel.myExpenses.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.start_date),
            amount = total,
            onNavigateClick = {}

        )
        TopGreenCard(
            title = stringResource(R.string.end_date),
            amount = total,
            onNavigateClick = {}
        )
        TopGreenCard(
            title = stringResource(R.string.sum),
            amount = total
        )

        LazyColumn {
            items(
                items = myExpenses,
                key = { expense -> expense.id }) { expense ->
                AppCard(
                    title = expense.category.name,
                    amount = expense.amount,
                    subAmount = "Октябрь 2022",
                    avatarEmoji = expense.category.emoji,
                    subtitle = expense.comment,
                    canNavigate = true,
                    onNavigateClick = {},
                )
            }
        }
    }
}
