package com.natan.shamilov.shmr25.account.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): List<Account> = repository.getAccountsList()
}
