package com.natan.shamilov.shmr25.presentation.screens.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.TopGreenCard

@Composable
fun ExpensesScreen(viewModel: ExpensesViewModel = hiltViewModel(), paddingValues: PaddingValues) {
    val total by viewModel.sumOfExpenses.collectAsStateWithLifecycle()
    val myExpenses by viewModel.myExpenses.collectAsStateWithLifecycle()

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
                    avatarEmoji = expense.category.emoji,
                    subtitle = expense.comment,
                    canNavigate = true,
                    onNavigateClick = {},
                )
            }
        }
    }
}