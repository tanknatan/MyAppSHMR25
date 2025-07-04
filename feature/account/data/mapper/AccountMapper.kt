package com.natan.shamilov.shmr25.feature.account.data.mapper

import com.natan.shamilov.shmr25.feature.account.data.model.AccountDto
import com.natan.shamilov.shmr25.common.Account
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun mapDtoToDomain(dto: AccountDto): Account = with(dto) {
        Account(
            id = id,
            name = name,
            balance = balance.toDouble(),
            currency = currency
        )
    }
} 