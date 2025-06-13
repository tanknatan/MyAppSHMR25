package com.natan.shamilov.shmr25.presentation.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.ui.TopGreenCard

@Composable
fun AccountScreen(viewModel: AccountViewModel = hiltViewModel(), paddingValues: PaddingValues) {
    val account by viewModel.account.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(paddingValues)) {
        account?.let {
            TopGreenCard(
                title = it.name,
                amount = account?.balance,
                canNavigate = true,
                onNavigateClick = {},
                avatarEmoji ="\uD83D\uDCB0"
            )
        }
        TopGreenCard(
            title = "Валюта",
            cucurrency = account?.currency,
            canNavigate = true,
            onNavigateClick = {},
        )
    }
}