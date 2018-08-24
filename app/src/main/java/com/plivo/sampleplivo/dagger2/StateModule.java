package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.vm.CallState;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StateModule {

    @Provides @Singleton
    public CallState createCallState() {
        return new CallState();
    }
}
