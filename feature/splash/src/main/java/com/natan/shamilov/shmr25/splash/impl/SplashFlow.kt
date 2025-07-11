package com.natan.shamilov.shmr25.splash.impl

import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen

sealed class SplashFlow(
    override val route: String,
) : Screen {
    object Splash : SplashFlow(SPLASH_ROUTE)

    companion object {
        const val SPLASH_ROUTE = "splash"
    }
}
