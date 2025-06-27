package com.natan.shamilov.shmr25.presentation.feature.account.domain.usecase

import com.natan.shamilov.shmr25.domain.entity.Account
import com.natan.shamilov.shmr25.presentation.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): List<Account> = repository.getAccountsList()
} 