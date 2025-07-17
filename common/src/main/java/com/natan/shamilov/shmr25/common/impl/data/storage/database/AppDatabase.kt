package com.natan.shamilov.shmr25.common.impl.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natan.shamilov.shmr25.common.impl.domain.storage.dao.TransactionsDao
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.AccountDao
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.CategoriesDao
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.AccountDbModel
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.CategoryDbModel
import com.natan.shamilov.shmr25.common.impl.domain.storage.entity.TransactionDbModel

@Database(
    version = 1,
    entities = [
        AccountDbModel::class,
        CategoryDbModel::class,
        TransactionDbModel::class
    ],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun transactionsDao(): TransactionsDao
}