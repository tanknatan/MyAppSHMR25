package com.natan.shamilov.shmr25.expenses.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider

interface ExpensesDependencies {

    val accountProvider: AccountProvider

    val categoriesProvider: CategoriesProvider

    val transactionsProvider: TransactionsProvider

    val hapticProvider: HapticProvider
}
