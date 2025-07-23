package com.natan.shamilov.shmr25.option.presentation.screen.mainColorOption

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
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.natan.shamilov.shmr25.common.impl.presentation.ui.CustomTopAppBar
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryBlack
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryBlue
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryGreen
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryPurple
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryRed
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryWhite
import com.natan.shamilov.shmr25.common.impl.presentation.ui.theme.PrimaryYellow
import com.natan.shamilov.shmr25.option.presentation.navigation.OptionsFlow

@Composable
fun ChangeMainColorScreen(onBackClick: () -> Unit) {
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

        ChangeMainColorContent(
            paddingValues = innerPadding,
        )
    }
}

@Composable
fun ChangeMainColorContent(paddingValues: PaddingValues) {
    var selectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .padding(paddingValues),
    ) {
        ColorRadioButtonsGrid(
            selectedIndex = selectedIndex,
            onSelectedIndexChange = { selectedIndex = it }
        )
    }
}

@Composable
fun ColorRadioButtonsGrid(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    buttonSize: Dp = 80.dp,
    colors: List<Color> = listOf(
        PrimaryGreen,
        PrimaryBlue,
        PrimaryRed,
        PrimaryYellow,
        PrimaryPurple,
        PrimaryBlack,
        PrimaryWhite
    ),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(colors) { index, item ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .size(buttonSize) // квадрат
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) Color.Black else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = item,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelectedIndexChange(index) }
            )
        }
    }
}
