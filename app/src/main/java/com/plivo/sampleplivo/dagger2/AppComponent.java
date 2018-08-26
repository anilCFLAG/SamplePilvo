package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.ui.CallActivity;
import com.plivo.sampleplivo.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
    NetworkModule.class,
        UtilsModule.class,
        ViewContextModule.class
}) @Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(CallActivity callActivity);
}
