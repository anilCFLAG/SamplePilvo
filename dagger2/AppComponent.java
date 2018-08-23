package com.example.plivo.sample1.dagger2;

import com.example.plivo.sample1.ui.MainActivity;
import com.example.plivo.sample1.vm.CallState;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
    NetworkModule.class,
        UtilsModule.class,
        StateModule.class
}) @Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
