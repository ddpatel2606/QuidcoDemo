package com.dixitpatel.quidcodemo.ui.tracks;

import com.dixitpatel.quidcodemo.ui.artists.ArtistsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TracksFragmentProvider {

    @ContributesAndroidInjector
    abstract TracksFragment contributeTracksFragment();
}
