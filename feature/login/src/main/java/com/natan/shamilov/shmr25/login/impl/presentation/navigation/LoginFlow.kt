package com.natan.shamilov.shmr25.login.impl.presentation.navigation

import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen

sealed class LoginFlow(
    override val route: String,
) : Screen {
    object Login : LoginFlow(LOGIN_ROUTE)

    companion object {
        const val LOGIN_ROUTE = "login"
    }
}
