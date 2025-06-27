package com.natan.shamilov.shmr25.presentation.feature.option.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.presentation.navigation.Screen
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.ui.ThemeCard

@Composable
fun OptionScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Options.startIcone,
                Screen.Options.title,
                Screen.Options.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->

        OptionsContent(
            paddingValues = innerPadding
        )
    }
}

@Composable
fun OptionsContent(paddingValues: PaddingValues) {
    var isDarkTheme by remember { mutableStateOf(false) }
    val optionList = listOf(
        R.string.main_color,
        R.string.sound,
        R.string.vibration,
        R.string.password,
        R.string.sync,
        R.string.language,
        R.string.about_app
    )
    Column(modifier = Modifier.padding(paddingValues)) {
        ThemeCard(
            title = stringResource(R.string.dark_theme),
            isDarkTheme = isDarkTheme,
            onToggle = { isDarkTheme = !isDarkTheme }
        )
        optionList.forEach { option ->
            AppCard(
                title = stringResource(option),
                canNavigate = true,
                onNavigateClick = {},
                isSetting = true
            )
        }
    }
}
