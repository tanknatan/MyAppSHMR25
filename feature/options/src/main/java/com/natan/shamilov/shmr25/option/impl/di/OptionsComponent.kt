package com.natan.shamilov.shmr25.option.impl.di

import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactory
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryModule
import com.natan.shamilov.shmr25.common.impl.di.ViewModelFactoryScope
import com.natan.shamilov.shmr25.option.api.OptionsDependencies
import dagger.Component

@OptionsScope
@ViewModelFactoryScope
@Component(
    dependencies = [
        OptionsDependencies::class
    ],
    modules = [
        ViewModelFactoryModule::class,
        OptionsViewModelModule::class
    ]
)
interface OptionsComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(deps: OptionsDependencies): OptionsComponent
    }
}