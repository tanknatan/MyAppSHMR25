package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Account
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    operator fun invoke(): List<Account> = repository.getAccountsList()
}