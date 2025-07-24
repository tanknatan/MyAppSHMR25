package com.natan.shamilov.shmr25.account.impl.presentation.screen.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.account.R
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AccountDropdownMenu
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.impl.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow
import com.natan.shamilov.shmr25.schedule.AccountSchedule
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = viewModel(factory = LocalViewModelFactory.current),
    onFABClick: () -> Unit,
    onEditAccountClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                AccountFlow.Account.startIcone,
                AccountFlow.Account.title,
                AccountFlow.Account.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = {
                    viewModel.selectedAccount.value?.id?.let {
                        onEditAccountClick(it.toString())
                    }
                }
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
                val accounts by viewModel.accounts.collectAsStateWithLifecycle()
                val selectedAccount by viewModel.selectedAccount.collectAsStateWithLifecycle()
                AccountContent(
                    paddingValues = innerPadding,
                    accounts = accounts,
                    selectedAccount = selectedAccount,
                    onAccountSelected = {
                        viewModel.selectAccount(it)
                    }

                )
            }
        }
    }
}

@Composable
fun AccountContent(
    paddingValues: PaddingValues,
    accounts: List<Account>,
    selectedAccount: Account?,
    onAccountSelected: (Account) -> Unit,
) {
    val (testExpenses, testIncome) = generateTestDataFor31Days()
    Column(
        modifier = Modifier.padding(
            paddingValues = paddingValues
        )
    ) {
        AccountDropdownMenu(
            accounts = accounts,
            selectedAccount = selectedAccount,
            onAccountSelected = {
                onAccountSelected(it)
            }
        )
        selectedAccount?.let { account ->
            TopGreenCard(
                title = account.name,
                amount = account.balance,
                currency = account.currency,
                canNavigate = true,
                onNavigateClick = {},
                avatarEmoji = stringResource(R.string.avatarEmoji)
            )

            TopGreenCard(
                title = stringResource(R.string.currency),
                cucurrency = account.currency,
                canNavigate = true,
                onNavigateClick = {}
            )
            AccountSchedule(
                expenses = testExpenses,
                incomes = testIncome
            )
        }
    }
}

fun generateTestDataFor31Days(): Pair<Map<String, Double>, Map<String, Double>> {
    val expenses = mutableMapOf<String, Double>()
    val incomes = mutableMapOf<String, Double>()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()

    for (i in 0 until 10) {
        val date = today.minusDays(i.toLong()).format(formatter)

        // Генерируем случайную сумму от 10 до 50_000_000
        val expense = Random.nextDouble(0.0, 50.0)
        val income = Random.nextDouble(0.0, 50.0)

        expenses[date] = expense
        incomes[date] = income
    }
    for (i in 11 until 20) {
        val date = today.minusDays(i.toLong()).format(formatter)

        // Генерируем случайную сумму от 10 до 50_000_000
        val expense = 0.0
        val income = 0.0

        expenses[date] = expense
        incomes[date] = income
    }
    for (i in 21 until 31) {
        val date = today.minusDays(i.toLong()).format(formatter)

        // Генерируем случайную сумму от 10 до 50_000_000
        val expense = Random.nextDouble(10000.0, 50_000_000.0)
        val income = Random.nextDouble(10000.0, 50_000_000.0)

        expenses[date] = expense
        incomes[date] = income
    }

    return Pair(expenses, incomes)
}

