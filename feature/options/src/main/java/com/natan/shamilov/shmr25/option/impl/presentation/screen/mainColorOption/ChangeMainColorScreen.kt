package com.natan.shamilov.shmr25.option.impl.presentation.screen.mainColorOption

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natan.shamilov.shmr25.common.impl.presentation.LocalViewModelFactory
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.MainColorType
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryBlue
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryGreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryRed
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryYellow
import com.natan.shamilov.shmr25.option.impl.presentation.navigation.OptionsFlow

@Composable
fun ChangeMainColorScreen(
    onBackClick: () -> Unit,
    viewModel: ChangeMainColorViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                OptionsFlow.MainColor.startIcone,
                OptionsFlow.MainColor.title,
                OptionsFlow.MainColor.endIcone,
                onBackOrCanselClick = { onBackClick() },
                onNavigateClick = { }
            )
        }
    ) { innerPadding ->
        val mainColor by viewModel.mainColor.collectAsStateWithLifecycle()
        ChangeMainColorContent(
            paddingValues = innerPadding,
            mainColor = mainColor,
            onSelectedChange = {
                viewModel.changeMainColor(it)
            }
        )
    }
}

@Composable
fun ChangeMainColorContent(
    paddingValues: PaddingValues,
    mainColor: MainColorType,
    onSelectedChange: (MainColorType) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        ColorRadioButtonsGrid(
            selected = mainColor,
            onSelectedChange = { onSelectedChange(it) },
        )
    }
}

@Composable
fun ColorRadioButtonsGrid(
    selected: MainColorType,
    onSelectedChange: (MainColorType) -> Unit,
    buttonSize: Dp = 80.dp,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(MainColorType.entries) {
            val isSelected = it == selected

            Box(
                modifier = Modifier
                    .size(buttonSize) // квадрат
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) Color.Black else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = when (it) {
                            MainColorType.GREEN -> PrimaryGreen
                            MainColorType.RED -> PrimaryRed
                            MainColorType.YELLOW -> PrimaryYellow
                            MainColorType.BLUE -> PrimaryBlue
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelectedChange(it) }
            )
        }
    }
}
