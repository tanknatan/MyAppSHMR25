package com.natan.shamilov.shmr25.presentation.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.natan.shamilov.shmr25.ui.AppCard
import com.natan.shamilov.shmr25.ui.SearchField

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel = hiltViewModel(), paddingValues: PaddingValues) {

    val myCategories by viewModel.myCategories.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(paddingValues)) {
        SearchField(
            query = query,
            onQueryChange = { query = it }
        )

        LazyColumn() {
            items(
                items = myCategories,
                key = { category -> category.id }
            ) { category ->
                AppCard(
                    title = category.name,
                    avatarEmoji = category.emoji,
                )
            }
        }
    }
}