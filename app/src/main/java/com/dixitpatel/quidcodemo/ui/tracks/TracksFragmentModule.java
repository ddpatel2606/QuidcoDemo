package com.dixitpatel.quidcodemo.ui.tracks;

import com.dixitpatel.quidcodemo.ui.artists.ArtistsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class TracksFragmentModule {

    @Provides
    TracksViewModel tracksViewModel() {
        return new TracksViewModel();
    }


}
