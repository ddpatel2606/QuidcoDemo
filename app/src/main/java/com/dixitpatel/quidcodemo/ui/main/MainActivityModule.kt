package com.dixitpatel.quidcodemo.ui.main

import androidx.lifecycle.ViewModelProvider
import com.dixitpatel.quidcodemo.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun providesViewModel(): MainActivityViewModel {
        return MainActivityViewModel()
    }

    @Provides
    fun provideViewModelProvider(viewModel: MainActivityViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}