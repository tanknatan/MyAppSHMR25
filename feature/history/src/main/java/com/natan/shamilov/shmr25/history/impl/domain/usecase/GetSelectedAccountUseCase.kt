package com.natan.shamilov.shmr25.history.impl.domain.usecase

import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.history.impl.domain.repository.AccountRepository
import javax.inject.Inject

class GetSelectedAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Account? = repository.getSelectedAccount()
}

