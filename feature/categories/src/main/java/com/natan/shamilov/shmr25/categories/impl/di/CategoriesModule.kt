package com.natan.shamilov.shmr25.categories.impl.di

import com.natan.shamilov.shmr25.feature.categories.data.api.CategoriesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object CategoriesModule {

    @Provides
    @CategoriesScope
    fun provideCategoriesApi(
        retrofit: Retrofit,
    ): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }
}
