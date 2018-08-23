package com.example.plivo.sample1.dagger2;

import com.example.plivo.sample1.vm.CallState;

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
