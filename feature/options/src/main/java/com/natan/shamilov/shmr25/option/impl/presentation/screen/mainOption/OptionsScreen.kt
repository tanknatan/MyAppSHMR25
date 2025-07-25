package com.natan.shamilov.shmr25.option.impl.presentation.screen.mainOption

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.localizedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.AppCard
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.impl.presentation.components.ThemeCard
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow
import com.natan.shamilov.shmr25.options.R

@Composable
fun OptionScreen(
    onChoseOption: (Int) -> Unit,
    viewModel: OptionsViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.Options.startIcone,
                OptionsFlow.Options.title,
                OptionsFlow.Options.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
        OptionsContent(
            paddingValues = innerPadding,
            onChoseOption = { onChoseOption(it) },
            isDarkTheme = isDarkTheme,
            onChangeTheme = { viewModel.setThemeMode(!isDarkTheme) },
        )
    }
}

@Composable
fun OptionsContent(
    paddingValues: PaddingValues,
    isDarkTheme: Boolean,
    onChoseOption: (Int) -> Unit,
    onChangeTheme: () -> Unit,
) {
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
            title = localizedString(R.string.dark_theme),
            isDarkTheme = isDarkTheme,
            onToggle = { onChangeTheme() }
        )
        optionList.forEach { option ->
            AppCard(
                title = localizedString(option),
                canNavigate = true,
                onNavigateClick = { onChoseOption(option) },
                isSetting = true
            )
        }
    }
}
