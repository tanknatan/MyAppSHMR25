package com.natan.shamilov.shmr25.expenses.impl.domain.usecase

import com.natan.shamilov.shmr25.expenses.impl.domain.repository.AccountRepository
import javax.inject.Inject

class SetSelectedAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Int) = repository.setSelectedAccount(accountId)
}
