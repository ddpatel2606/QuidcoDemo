package com.dixitpatel.quidcodemo.ui.albums;

import com.dixitpatel.quidcodemo.ui.artists.ArtistsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumsFragmentModule {

    @Provides
    AlbumsViewModel albumsViewModel() {
        return new AlbumsViewModel();
    }


}
