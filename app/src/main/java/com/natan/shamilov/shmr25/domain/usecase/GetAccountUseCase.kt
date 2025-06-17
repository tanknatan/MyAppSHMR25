package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.domain.FinanceRepository
import com.natan.shamilov.shmr25.domain.entity.Account
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    operator fun invoke(): Account {
        return repository.getAccount()
    }
}