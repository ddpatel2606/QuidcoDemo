package com.dixitpatel.quidcodemo.ui.login

import androidx.lifecycle.ViewModelProvider
import com.dixitpatel.quidcodemo.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {

    @Provides
    fun providesViewModel(): LoginActivityViewModel {
        return LoginActivityViewModel()
    }

    @Provides
    fun provideViewModelProvider(viewModel: LoginActivityViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}