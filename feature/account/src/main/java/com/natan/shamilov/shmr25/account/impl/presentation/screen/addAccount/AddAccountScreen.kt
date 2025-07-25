package com.natan.shamilov.shmr25.account.impl.presentation.screen.addAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.account.R
import com.natan.shamilov.shmr25.common.impl.domain.entity.State
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AccountNameInput
import com.natan.shamilov.shmr25.common.impl.presentation.ui.BalanceInput
import com.natan.shamilov.shmr25.account.impl.presentation.components.CurrencyBottomSheet
import com.natan.shamilov.shmr25.account.impl.presentation.components.CurrencySelectorButton
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomButton
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.LoadingScreen
import com.natan.shamilov.shmr25.feature.account.presentation.navigation.AccountFlow
import kotlinx.coroutines.launch

/**
 * Composable функция для экрана добавления нового счета.
 * Ответственность: Отображение UI формы создания счета, включая поля ввода,
 * обработку пользовательского ввода и отображение различных состояний экрана
 * (загрузка, ошибка, контент).
 */
@Composable
fun AddAccountScreen(
    viewModel: AddAccountViewModel = viewModel(factory = LocalViewModelFactory.current),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                AccountFlow.AddAccount.startIcone,
                AccountFlow.AddAccount.title,
                AccountFlow.AddAccount.endIcone,
                onBackOrCanselClick = {
                    viewModel.vibrate()
                    onBackPressed() },
                onNavigateClick = { },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is State.Loading -> {
                LoadingScreen(innerPadding = innerPadding)
            }

            is State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = localizedString(R.string.not_network))
                }
            }

            is State.Content -> {
                AddAccountContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    onBack = { onBackPressed() },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountContent(
    viewModel: AddAccountViewModel,
    paddingValues: PaddingValues,
    onBack: () -> Unit,
) {
    var accountName by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf<com.natan.shamilov.shmr25.common.impl.domain.entity.CurrencyOption?>(null) }

    val isFormValid by remember(accountName, balance, selectedCurrency) {
        derivedStateOf {
            accountName.isNotBlank() &&
                    balance.isNotBlank() &&
                    selectedCurrency != null
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        CurrencyBottomSheet(
            onDismiss = {
                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                }
            },
            onCurrencySelected = {
                selectedCurrency = it
                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                }
            },
            sheetState = sheetState
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        AccountNameInput(
            accountName = accountName,
            onNameChange = { accountName = it },
            isError = accountName.isBlank()
        )
        BalanceInput(
            balance = balance,
            onBalanceChange = { balance = it },
            isError = balance.isBlank()
        )
        Spacer(Modifier.height(12.dp))
        CurrencySelectorButton(
            selectedCurrency = selectedCurrency,
            onClick = {
                coroutineScope.launch {
                    showBottomSheet = true
                    sheetState.show()
                }
            }
        )
        Spacer(Modifier.height(20.dp))
        CustomButton(
            onButtonClick = {
                viewModel.createAccount(
                    name = accountName,
                    balance = balance,
                    currency = selectedCurrency!!.code,
                    onSuccess = { onBack() }
                )
            },
            isEnabled = isFormValid,
            text = localizedString(R.string.create_account)
        )
    }
}
