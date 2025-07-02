package com.natan.shamilov.shmr25.feature.categories.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.app.navigation.Screen
import com.natan.shamilov.shmr25.common.State
import com.natan.shamilov.shmr25.common.ui.AppCard
import com.natan.shamilov.shmr25.common.ui.CustomSearchBar
import com.natan.shamilov.shmr25.common.ui.CustomTopAppBar

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Инициализируем ViewModel при первом показе экрана
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Screen.Categories.startIcone,
                Screen.Categories.title,
                Screen.Categories.endIcone,
                onBackOrCanselClick = {},
                onNavigateClick = { }
            )
        }
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
                CategoriesContent(
                    paddingValues = innerPadding,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun CategoriesContent(
    viewModel: CategoriesViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val filteredCategories by viewModel.filteredCategories.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(paddingValues)) {
        CustomSearchBar(
            query = query,
            onValueChange = { viewModel.setQuery(it) }
        )

        LazyColumn {
            items(
                items = filteredCategories,
                key = { category -> category.id }
            ) { category ->
                AppCard(
                    title = category.name,
                    avatarEmoji = category.emoji
                )
            }
        }
    }
}
