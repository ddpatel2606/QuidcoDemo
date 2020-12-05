package com.dixitpatel.quidcodemo.dagger.modules;

import android.content.Context;

import com.dixitpatel.quidcodemo.application.MyApplication;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonAppModule {

    @Singleton
    @Provides
    Context provideContext(MyApplication application) {
        return application;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

}
