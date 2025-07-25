package com.natan.shamilov.shmr25.option.impl.presentation.screen.languageOption

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow

@Composable
fun ChangeLanguageScreen(
    onBackClick: () -> Unit,
    viewModel: ChangeLocaleViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.Language.startIcone,
                OptionsFlow.Language.title,
                OptionsFlow.Language.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        val language by viewModel.locale.collectAsStateWithLifecycle()
        ChangeLanguageContent(
            paddingValues = innerPadding,
            currentLanguage = language,
            onToggle = { viewModel.setLocale(locale = it) }
        )
    }
}

@Composable
fun ChangeLanguageContent(currentLanguage: String, paddingValues: PaddingValues, onToggle: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LanguageSwitcher(
            currentLanguage = currentLanguage,
            onToggle = { onToggle(it) }
        )
    }
}

@Composable
fun LanguageSwitcher(
    currentLanguage: String,
    onToggle: (String) -> Unit,
) {
    val selected = currentLanguage.lowercase()
    val indicatorOffset by animateDpAsState(
        targetValue = if (selected == "ru") 0.dp else 64.dp,
        label = "language-switch-offset"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(64.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
            )
            Row(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onToggle("ru") }
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RU",
                        color = if (selected == "ru") Color.Black else Color.DarkGray,
                        fontWeight = if (selected == "ru") FontWeight.Bold else FontWeight.Normal
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onToggle("en") }
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "EN",
                        color = if (selected == "en") Color.Black else Color.DarkGray,
                        fontWeight = if (selected == "en") FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
