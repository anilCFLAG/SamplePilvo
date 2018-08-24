package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.utils.AppUtils;

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
