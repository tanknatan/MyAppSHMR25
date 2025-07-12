package com.natan.shamilov.shmr25.feature.account.domain.usecase

import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import javax.inject.Inject

class EditAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(
        accountId: Int,
        name: String,
        balance: String,
        currency: String
    ): com.natan.shamilov.shmr25.common.impl.data.model.Result<Unit> {
        return repository.editAccount(
            accountId = accountId,
            name = name,
            balance = balance,
            currency = currency
        )
    }
}
