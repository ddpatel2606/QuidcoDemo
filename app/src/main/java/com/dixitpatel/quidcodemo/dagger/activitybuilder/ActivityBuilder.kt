package com.dixitpatel.quidcodemo.dagger.activitybuilder

import com.dixitpatel.quidcodemo.constant.LOGIN_METHOD
import com.dixitpatel.quidcodemo.ui.albums.AlbumsFragmentProvider
import com.dixitpatel.quidcodemo.ui.artists.ArtistsFragmentProvider
import com.dixitpatel.quidcodemo.ui.detail.DetailViewActivity
import com.dixitpatel.quidcodemo.ui.detail.DetailViewActivityModule
import com.dixitpatel.quidcodemo.ui.login.LoginActivity
import com.dixitpatel.quidcodemo.ui.login.LoginActivityModule
import com.dixitpatel.quidcodemo.ui.main.MainActivity
import com.dixitpatel.quidcodemo.ui.main.MainActivityModule
import com.dixitpatel.quidcodemo.ui.tracks.TracksFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This is Dagger Activity Builder Which ever activity will be used all activity should be mention
 * here as a Dagger Module.
 */
@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = [MainActivityModule::class,
        AlbumsFragmentProvider::class,
        TracksFragmentProvider::class,
        ArtistsFragmentProvider::class])
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity?

    @ContributesAndroidInjector(modules = [DetailViewActivityModule::class])
    abstract fun contributeDetailActivity(): DetailViewActivity?
}