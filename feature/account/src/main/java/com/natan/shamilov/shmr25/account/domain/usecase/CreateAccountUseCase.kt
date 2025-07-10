package com.natan.shamilov.shmr25.feature.account.domain.usecase

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(
        name: String,
        balance: String,
        currency: String
    ): Result<Unit> = repository.createAccount(name, balance, currency)
}
