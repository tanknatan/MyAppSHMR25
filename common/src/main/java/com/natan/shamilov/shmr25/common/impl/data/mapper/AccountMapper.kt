package com.natan.shamilov.shmr25.common.impl.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.AccountDto
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.AccountDbModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.presentation.utils.convertCurrency
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun mapAccountDtoToDomain(dto: AccountDto): Account = with(dto) {
        Account(
            id = id,
            name = name,
            balance = balance.toDouble(),
            currency = currency.convertCurrency()
        )
    }

    fun mapDomainToDb(account: Account): AccountDbModel {
        return AccountDbModel(
            id = account.id,
            name = account.name,
            balance = account.balance,
            currency = account.currency,
        )
    }

    fun mapDbToDomain(dbModel: AccountDbModel): Account {
        return Account(
            id = dbModel.id,
            name = dbModel.name,
            balance = dbModel.balance,
            currency = dbModel.currency
        )
    }

    fun mapDbToDomainList(dbList: List<AccountDbModel>): List<Account> {
        return dbList.map { mapDbToDomain(it) }
    }
}
