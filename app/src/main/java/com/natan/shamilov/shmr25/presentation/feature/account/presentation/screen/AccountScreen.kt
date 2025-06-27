package com.natan.shamilov.shmr25.presentation.feature.account.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.ui.AccountDropdownMenu
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.MyFloatingActionButton
import com.natan.shamilov.shmr25.ui.TopGreenCard

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    onFABClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Инициализируем ViewModel при первом показе экрана
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Account.startIcone,
                Screen.Account.title,
                Screen.Account.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { }
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
                AccountContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun AccountContent(viewModel: AccountViewModel, paddingValues: PaddingValues) {
    val accounts by viewModel.account.collectAsStateWithLifecycle()
    val selectedAccount by viewModel.selectedAccount.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(paddingValues)) {
        AccountDropdownMenu(
            accounts = accounts,
            selectedAccount = selectedAccount,
            onAccountSelected = { viewModel.selectAccount(it) }
        )
        selectedAccount?.let { account ->
            TopGreenCard(
                title = account.name,
                amount = account.balance,
                currency = account.currency,
                canNavigate = true,
                onNavigateClick = {},
                avatarEmoji = "\uD83D\uDCB0"
            )

            TopGreenCard(
                title = "Валюта",
                cucurrency = account.currency,
                canNavigate = true,
                onNavigateClick = {}
            )
        }
    }
}
