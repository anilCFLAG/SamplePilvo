package com.example.plivo.sample1.dagger2;

import com.example.plivo.sample1.utils.AppUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides @Singleton
    public AppUtils createAppUtils() {
        return new AppUtils();
    }
}
