package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import javax.inject.Inject

class LoadAccountsListUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.loadAccountsList()
    }
}