package com.natan.shamilov.shmr25.app.di.moduls

import com.natan.shamilov.shmr25.app.di.AppComponent
import com.natan.shamilov.shmr25.categories.api.CategoriesDependencies
import dagger.Binds
import dagger.Module

@Module
interface CategoriesDependenciesModule {

    @Binds
    fun bindCategoriesDependencies(deps: AppComponent): CategoriesDependencies
}
