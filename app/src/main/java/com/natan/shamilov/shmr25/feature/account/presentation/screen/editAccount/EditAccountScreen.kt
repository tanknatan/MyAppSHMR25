package com.natan.shamilov.shmr25.feature.account.presentation.screen.editAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.app.navigation.Screen
import com.natan.shamilov.shmr25.common.domain.entity.CurrencyOption
import com.natan.shamilov.shmr25.common.domain.entity.State
import com.natan.shamilov.shmr25.common.ui.AccountNameInput
import com.natan.shamilov.shmr25.common.ui.BalanceInput
import com.natan.shamilov.shmr25.common.ui.CurrencyBottomSheet
import com.natan.shamilov.shmr25.common.ui.CurrencySelectorButton
import com.natan.shamilov.shmr25.common.ui.CustomButton
import com.natan.shamilov.shmr25.common.ui.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
fun EditAccountScreen(
    accountId: String,
    viewModel: EditAccountViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(accountId) {
        viewModel.loadAccount(accountId)
    }
    val accountName by viewModel.accountName.collectAsStateWithLifecycle()
    val balance by viewModel.balance.collectAsStateWithLifecycle()
    val selectedCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()

    val isFormValid by remember(accountName, balance, selectedCurrency) {
        derivedStateOf {
            accountName.isNotBlank() &&
                balance.isNotBlank() &&
                selectedCurrency != null
        }
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.EditAccount.startIcone,
                Screen.EditAccount.title,
                Screen.EditAccount.endIcone,
                onBackOrCanselClick = { onBackPressed() },
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
                    Text(text = "Нет сети")
                }
            }

            is State.Content -> {
                EditAccountContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    onBackPressed = {
                        onBackPressed()
                    },
                    accountName = accountName,
                    balance = balance,
                    selectedCurrency = selectedCurrency,
                    isFormValid = isFormValid
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountContent(
    viewModel: EditAccountViewModel,
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    accountName: String,
    balance: String,
    selectedCurrency: CurrencyOption?,
    isFormValid: Boolean,
) {
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
                viewModel.onCurrencyChange(it)
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
            onNameChange = { viewModel.onAccountNameChange(it) },
            isError = accountName.isBlank()
        )
        BalanceInput(
            balance = balance,
            onBalanceChange = { viewModel.onBalanceChange(it) },
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
                viewModel.editAccount(
                    name = accountName,
                    balance = balance,
                    currency = selectedCurrency!!.code,
                    onSuccess = { onBackPressed() }
                )
            },
            text = stringResource(R.string.save),
            isEnabled = isFormValid
        )
    }
}
