package com.dixitpatel.quidcodemo.ui.artists;

import dagger.Module;
import dagger.Provides;

@Module
public class ArtistsFragmentModule {

    @Provides
    ArtistsViewModel artistsViewModel() {
        return new ArtistsViewModel();
    }


}
