package com.natan.shamilov.shmr25.option.impl.presentation.screen.aboutAppOption

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow
import com.natan.shamilov.shmr25.option.impl.presentation.utils.getAppInfo

@Composable
fun AboutAppScreen(
    onBackClick: () -> Unit,
    viewModel: AboutAppViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    val context = LocalContext.current
    val lastUpdate = remember { context.getAppInfo() }
    val appVersion by viewModel.appInfo.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.AboutApp.startIcone,
                OptionsFlow.AboutApp.title,
                OptionsFlow.AboutApp.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->

        AboutAppContent(
            paddingValues = innerPadding,
            appVersion = appVersion,
            lastUpdate = lastUpdate
        )
    }
}

@Composable
fun AboutAppContent(paddingValues: PaddingValues, appVersion: String, lastUpdate: String) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        Text(text = appVersion)
        Text(text = lastUpdate)
    }
}