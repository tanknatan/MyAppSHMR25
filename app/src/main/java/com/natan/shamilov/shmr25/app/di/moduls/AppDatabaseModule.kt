package com.natan.shamilov.shmr25.app.di.moduls

import android.content.Context
import androidx.room.Room
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.AccountDao
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.CategoriesDao
import com.natan.shamilov.shmr25.common.impl.data.storage.database.AppDatabase
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.common.impl.domain.storage.dao.TransactionsDao
import dagger.Module
import dagger.Provides

@Module
class AppDatabaseModule {

    @Provides
    @ViewModelFactoryScope
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "finance_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoriesDao = database.categoriesDao()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionsDao = database.transactionsDao()
}
