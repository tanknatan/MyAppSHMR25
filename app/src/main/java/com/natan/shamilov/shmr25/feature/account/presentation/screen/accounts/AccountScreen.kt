package com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts

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
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.app.di.ApplicationHolder
import com.natan.shamilov.shmr25.app.di.DaggerViewModelFactory
import com.natan.shamilov.shmr25.common.domain.entity.Account
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.feature.account.presentation.components.AccountDropdownMenu
import com.natan.shamilov.shmr25.common.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.presentation.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = viewModel(factory = DaggerViewModelFactory(ApplicationHolder.application)),
    onFABClick: () -> Unit,
    onEditAccountClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Инициализируем ViewModel при первом показе экрана
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
    Column(modifier = Modifier.padding(paddingValues)) {
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
        }
    }
}
