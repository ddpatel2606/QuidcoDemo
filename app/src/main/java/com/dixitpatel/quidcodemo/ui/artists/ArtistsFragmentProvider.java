package com.dixitpatel.quidcodemo.ui.artists;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ArtistsFragmentProvider {

    @ContributesAndroidInjector
    abstract ArtistsFragment contributeArtistsFragment();
}
