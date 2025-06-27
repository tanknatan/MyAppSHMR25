package com.natan.shamilov.shmr25.feature.account.presentation.navigation

import com.natan.shamilov.shmr25.common.Screen

sealed class AccountFlow(override val route: String) : Screen {

    data object AccountGraph : AccountFlow(ACCOUNT_GRAPH)

    data object Account : AccountFlow(ACCOUNT_ROUTE)

    data object AddAccount : AccountFlow(ADD_ACCOUNT_ROUTE)

    companion object {
        const val ACCOUNT_GRAPH = "account_graph"
        const val ACCOUNT_ROUTE = "account_route"
        const val ADD_ACCOUNT_ROUTE = "add_account_route"
    }
}
