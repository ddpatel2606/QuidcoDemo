package com.dixitpatel.quidcodemo.ui.albums;

import com.dixitpatel.quidcodemo.ui.artists.ArtistsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlbumsFragmentProvider {

    @ContributesAndroidInjector
    abstract AlbumsFragment contributeAlbumsFragment();
}
