package com.natan.shamilov.shmr25.common.impl.data.repository

import android.util.Log
import com.natan.shamilov.shmr25.common.api.AccountProvider
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.impl.data.api.BaseAccountApi
import com.natan.shamilov.shmr25.common.impl.data.mapper.AccountMapper
import com.natan.shamilov.shmr25.common.impl.data.model.AccountRequestBody
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.AccountDao
import com.natan.shamilov.shmr25.common.impl.domain.entity.Account
import com.natan.shamilov.shmr25.common.impl.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BaseAccountRepositoryImpl @Inject constructor(
    private val api: BaseAccountApi,
    private val mapper: AccountMapper,
    private val networkRepository: NetworkChekerProvider,
    private val accountDao: AccountDao,
) : AccountRepository, AccountProvider {

    private val accounts = MutableStateFlow<List<Account>>(emptyList())
    private var selectedAccount = MutableStateFlow<Account?>(null)

    override suspend fun loadAccounts(): Result<List<Account>> {
        Log.d("LoadFromDbTest", networkRepository.getNetworkStatus().value.toString())
        return if (networkRepository.getNetworkStatus().value) {
            loadAccountsFromNetwork()
        } else {
            loadAccountsFromDb()
        }
    }

    override suspend fun getAccountsList(): List<Account> {
        Log.d("loadtest", accounts.value.toString())
        return accounts.value
    }

    override suspend fun getAccountsFlow(): StateFlow<List<Account>> = accounts

    override fun setSelectedAccount(accountId: Int) {
        selectedAccount.value = accounts.value.find { it.id == accountId }
    }

    override suspend fun getSelectedAccountFlow(): StateFlow<Account?> = selectedAccount

    override suspend fun getSelectedAccount(): Account? = selectedAccount.value

    private fun updateSelectedAccount() {
        selectedAccount.value?.let { selected ->
            selectedAccount.value = accounts.value.find { it.id == selected.id }
        }
    }

    private suspend fun loadAccountsFromNetwork(): Result<List<Account>> = Result.execute {
        val accountDtos = api.getAccountsList()
        Log.d("Load", accountDtos.toString())
        val domainAccounts = accountDtos.map { dto ->
            mapper.mapAccountDtoToDomain(dto)
        }

        // Сохраняем в БД
        val dbAccounts = domainAccounts.map { account ->
            mapper.mapDomainToDb(account)
        }
        accountDao.insertAccounts(dbAccounts)

        accounts.value = domainAccounts
        selectedAccount.value = accounts.value.first()
        updateSelectedAccount()
        Log.d("loadtest", accounts.value.toString())
        domainAccounts
    }

    private suspend fun loadAccountsFromDb(): Result<List<Account>> = Result.execute {
        val dbAccounts = accountDao.getAllAccounts()
        val domainAccounts = mapper.mapDbToDomainList(dbAccounts)

        accounts.value = domainAccounts
        selectedAccount.value = accounts.value.first()
        updateSelectedAccount()
        domainAccounts
    }

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String,
    ): Result<Unit> {
        val result = Result.execute {
            api.createAccount(
                AccountRequestBody(
                    name = name,
                    balance = balance,
                    currency = currency
                )
            )
        }
        if (result is Result.Success) {
            loadAccounts()
            setSelectedAccount(accounts.value.last().id)
        }
        return result
    }

    override suspend fun deleteAccount(id: Int): Result<Unit> {
        val result = Result.execute {
            api.deleteAccount(id)
        }
        return result
    }

    override suspend fun editAccount(
        accountId: Int,
        name: String,
        balance: String,
        currency: String,
    ): Result<Unit> {
        val result = Result.execute {
            api.editAccount(
                accoutId = accountId,
                AccountRequestBody(
                    name = name,
                    balance = balance,
                    currency = currency
                )
            )
        }
        if (result is Result.Success) {
            loadAccounts()
            setSelectedAccount(selectedAccount.value!!.id)
        }
        return result
    }
}
