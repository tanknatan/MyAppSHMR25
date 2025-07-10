package com.natan.shamilov.shmr25.feature.account.domain.usecase

import com.natan.shamilov.shmr25.common.domain.entity.Account
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSelectedAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): StateFlow<Account?> = repository.observeSelectedAccount()
} 