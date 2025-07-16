package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.history.impl.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(id: Int): Account? = repository.getAccountById(id)
}