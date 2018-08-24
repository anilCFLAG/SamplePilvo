package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.dagger2.NetworkModule;
import com.plivo.sampleplivo.dagger2.StateModule;
import com.plivo.sampleplivo.dagger2.UtilsModule;
import com.plivo.sampleplivo.ui.MainActivity;
import com.plivo.sampleplivo.vm.CallState;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
    NetworkModule.class,
        UtilsModule.class,
        StateModule.class,
        ViewContextModule.class
}) @Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
