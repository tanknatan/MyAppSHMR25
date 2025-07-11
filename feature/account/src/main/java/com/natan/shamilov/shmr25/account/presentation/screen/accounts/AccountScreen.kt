package com.natan.shamilov.shmr25.account.presentation.screen.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.account.R
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.ErrorScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.common.impl.presentation.ui.TopGreenCard
import com.natan.shamilov.shmr25.feature.account.presentation.components.AccountDropdownMenu
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow
import com.natan.shamilov.shmr25.feature.account.presentation.screen.accounts.AccountViewModel

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
