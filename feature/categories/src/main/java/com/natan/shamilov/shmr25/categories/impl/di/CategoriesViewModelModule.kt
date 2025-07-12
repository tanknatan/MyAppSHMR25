package com.natan.shamilov.shmr25.categories.impl.di

import androidx.lifecycle.ViewModel
import com.natan.shamilov.shmr25.common.impl.di.ViewModelKey
import com.natan.shamilov.shmr25.categories.impl.presentation.screen.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoriesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
}
