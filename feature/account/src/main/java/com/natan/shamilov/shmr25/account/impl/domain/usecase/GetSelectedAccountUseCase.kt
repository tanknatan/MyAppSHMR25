package com.natan.shamilov.shmr25.account.impl.domain.usecase

import com.natan.shamilov.shmr25.account.impl.domain.repository.AccountRepository
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import javax.inject.Inject

class GetSelectedAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Account? = repository.getSelectedAccount()
}

