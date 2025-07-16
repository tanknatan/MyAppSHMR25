package com.natan.shamilov.shmr25.history.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi

interface HistoryDependencies {

    val accountProvider: AccountProvider

    val transactionsApi: TransactionsApi

    val categoriesProvider: CategoriesProvider

    val transactionsProvider: TransactionsProvider
}
