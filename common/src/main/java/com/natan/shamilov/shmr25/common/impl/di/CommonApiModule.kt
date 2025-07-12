package com.natan.shamilov.shmr25.common.impl.di

import com.natan.shamilov.shmr25.common.impl.data.api.BaseAccountApi
import com.natan.shamilov.shmr25.common.impl.data.api.CategoriesApi
import com.natan.shamilov.shmr25.common.impl.data.api.TransactionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object CommonApiModule {
    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): BaseAccountApi =
        retrofit.create(BaseAccountApi::class.java)

    @Provides
    @Singleton
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi =
        retrofit.create(TransactionsApi::class.java)

    @Provides
    @Singleton
    fun provideCategoriesApi(
        retrofit: Retrofit,
    ): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }
}
