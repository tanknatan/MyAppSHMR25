package com.natan.shamilov.shmr25.splash.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.api.OptionsProvider

interface SplashDependencies {

    val accountProvider: AccountProvider

    val categoriesProvider: CategoriesProvider

    val optionsProvider: OptionsProvider
}
