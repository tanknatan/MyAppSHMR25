package com.natan.shamilov.shmr25.splash.impl

import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import javax.inject.Inject

class CategoriesStartupLoader @Inject constructor(
    private val categoriesProvider: CategoriesProvider
) {
    suspend fun load() = categoriesProvider.getCategoriesList()
}
