package com.dixitpatel.quidcodemo.ui.detail

import androidx.lifecycle.ViewModelProvider
import com.dixitpatel.quidcodemo.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class DetailViewActivityModule {

    @Provides
    fun providesViewModel(): DetailViewModel {
        return DetailViewModel()
    }

    @Provides
    fun provideViewModelProvider(viewModel: DetailViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}