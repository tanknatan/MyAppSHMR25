package com.natan.shamilov.shmr25.incomes.impl.domain.usecase

import com.natan.shamilov.shmr25.incomes.impl.domain.repository.AccountRepository
import javax.inject.Inject

class SetSelectedAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Int) = repository.setSelectedAccount(accountId)
}
