package com.natan.shamilov.shmr25.common.impl.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.AccountDbModel

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountDbModel>)

    @Update
    fun editAccount(editedAccount: AccountDbModel)

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountDbModel>

    @Query("DELETE FROM accounts")
    suspend fun clearAll()
}