package com.dixitpatel.quidcodemo.dagger.components

import com.dixitpatel.quidcodemo.application.MyApplication
import com.dixitpatel.quidcodemo.dagger.activitybuilder.ActivityBuilder
import com.dixitpatel.quidcodemo.dagger.modules.CommonAppModule
import com.dixitpatel.quidcodemo.dagger.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * This is MainAppComponent class where all modules are defined
 * i.e. ActivityModules, CommonAppModule, NetworkModule.
 */
@Singleton
@Component(modules = [  AndroidSupportInjectionModule::class,
                        NetworkModule::class,
                        CommonAppModule::class,
                        ActivityBuilder::class])
interface MainAppComponent : AndroidInjector<MyApplication?>
{
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MyApplication?> {}

    fun create(): MainAppComponent?
}