package com.natan.shamilov.shmr25.common.impl.data.repository

import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.impl.data.api.BaseAccountApi
import com.natan.shamilov.shmr25.common.impl.data.mapper.AccountMapper
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BaseAccountRepositoryImpl @Inject constructor(
    private val api: BaseAccountApi,
    private val mapper: AccountMapper,
) : AccountRepository, AccountProvider {
    private val _accountsList = MutableStateFlow<List<Account>>(emptyList())
    override val accountsList: StateFlow<List<Account>> = _accountsList.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    override val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    override suspend fun getAccountsList(): List<Account> = withContext(Dispatchers.IO) {
        if (_accountsList.value.isEmpty()) {
            loadAccountsList()
        }
        accountsList.value
    }

    override suspend fun loadAccountsList(): Result<Unit> = Result.execute {
        val dtos = api.getAccountsList()
        _accountsList.value = dtos.map { dto ->
            mapper.mapDtoToDomain(dto)
        }
        if (_selectedAccount.value == null && _accountsList.value.isNotEmpty()) {
            _selectedAccount.value = _accountsList.value.first()
        }
        Unit
    }

    override fun getSelectedAccount(): Account? = selectedAccount.value

    override fun setSelectedAccount(accountId: Int) {
        _selectedAccount.value = _accountsList.value.find { it.id == accountId }
    }
}
