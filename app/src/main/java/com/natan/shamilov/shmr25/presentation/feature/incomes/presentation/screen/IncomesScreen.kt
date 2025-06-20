package com.natan.shamilov.shmr25.presentation.feature.incomes.presentation.screen

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
fun IncomesTodayScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomesViewModel = hiltViewModel(LocalActivity.current!! as MainActivity),
    onHistoryClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Incomes.startIcone,
                Screen.Incomes.title,
                Screen.Incomes.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { onHistoryClick()},
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                )
            ) {
                Image(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
            }

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
                    Text(text = "Error") // Пока так
                }
            }

            is State.Content -> {

                IncomesContent(
                    viewModel = viewModel,
                    paddingValues = innerPadding,
                )
            }
        }
    }
}

@Composable
fun IncomesContent(viewModel: IncomesViewModel, paddingValues: PaddingValues) {
    val total by viewModel.sumOfIncomes.collectAsStateWithLifecycle()
    val myExpenses by viewModel.myIncomes.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(paddingValues)) {
        TopGreenCard(
            title = stringResource(R.string.total),
            amount = total
        )

        LazyColumn {
            items(
                items = myExpenses,
                key = { expense -> expense.id }) { expense ->
                AppCard(
                    title = expense.category.name,
                    amount = expense.amount,
                    canNavigate = true,
                    onNavigateClick = {}
                )
            }
        }
    }
}