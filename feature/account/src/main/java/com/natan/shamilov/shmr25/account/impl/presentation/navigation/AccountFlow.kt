package com.natan.shamilov.shmr25.feature.account.presentation.navigation

import com.natan.shamilov.shmr25.account.R
import com.natan.shamilov.shmr25.common.impl.domain.entity.Screen

sealed class AccountFlow(
    override val route: String,
    val title: Int? = null,
    val endIcone: Int? = null,
    val startIcone: Int? = null,
) : Screen {

    data object AccountGraph : AccountFlow(ACCOUNT_GRAPH)

    data object Account : AccountFlow(
        route = ACCOUNT_ROUTE,
        title = R.string.my_account,
        R.drawable.ic_edit,
        null
    )

    data object AddAccount : AccountFlow(
        route = ADD_ACCOUNT_ROUTE,
        title = R.string.add_account,
        null,
        R.drawable.ic_close
    )

    data object EditAccount : AccountFlow(
        route = EDIT_ACCOUNT_ROUTE,
        title = R.string.edit_account,
        null,
        R.drawable.ic_back
    )

    companion object {
        const val ACCOUNT_GRAPH = "account_graph"
        const val ACCOUNT_ROUTE = "account_route"
        const val ADD_ACCOUNT_ROUTE = "add_account_route"
        const val EDIT_ACCOUNT_ROUTE = "edit_account_route"
    }
}
