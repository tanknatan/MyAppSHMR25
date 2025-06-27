package com.natan.shamilov.shmr25.presentation.feature.account.domain.repository

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.entity.Account

interface AccountRepository {
    suspend fun getAccountsList(): List<Account>
    suspend fun loadAccountsList(): Result<Unit>
    suspend fun createAccount(name: String, balance: String, currency: String): Result<Unit>
    suspend fun deleteAccount(id: Int): Result<Unit>
} 