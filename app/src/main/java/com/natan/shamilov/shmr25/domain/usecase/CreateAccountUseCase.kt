package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(
        name: String,
        balance: String,
        currency: String
    ): Result<Unit> {
        return repository.createAccount(
            name = name,
            balance = balance,
            currency = currency
        )
    }
}