package com.natan.shamilov.shmr25.presentation.feature.account.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.presentation.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class LoadAccountsListUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.loadAccountsList()
} 