package com.natan.shamilov.shmr25.account.impl.domain.usecase

import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import javax.inject.Inject

class LoadAccountListUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Result<Unit> = repository.loadAccountsList()
}
