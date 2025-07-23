package com.natan.shamilov.shmr25.option.presentation.screen.aboutAppOption

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.presentation.navigation.OptionsFlow

@Composable
fun AboutAppScreen(onBackClick: () -> Unit) {
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
        )
    }
}

@Composable
fun AboutAppContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        Text("About App")
        Text("About App")

    }
}