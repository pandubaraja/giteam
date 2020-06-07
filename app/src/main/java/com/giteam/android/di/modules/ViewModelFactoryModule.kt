package com.giteam.android.di.modules

import androidx.lifecycle.ViewModelProvider
import com.giteam.android.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}