package com.natan.shamilov.shmr25.domain.usecase

import com.natan.shamilov.shmr25.data.api.Result
import com.natan.shamilov.shmr25.domain.FinanceRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend operator fun invoke(id: Int): Result<Unit> {
        return repository.deleteAccount(id)
    }
}
