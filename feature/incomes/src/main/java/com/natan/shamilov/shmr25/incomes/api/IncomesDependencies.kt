package com.natan.shamilov.shmr25.incomes.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi

interface IncomesDependencies {

    val accountProvider: AccountProvider

    val networkChekerProvider: NetworkChekerProvider

    val transactionsApi: TransactionsApi

    val categoriesProvider: CategoriesProvider

    val transactionsProvider: TransactionsProvider
}
