package com.natan.shamilov.shmr25.account.api

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.HapticProvider
import com.natan.shamilov.shmr25.common.api.TransactionsProvider

interface AccountDependencies {

    val accountProvider: AccountProvider

    val transactionProvider: TransactionsProvider

    val hapticProvider: HapticProvider
}
