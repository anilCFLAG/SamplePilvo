package com.example.plivo.sample1;

import android.app.Application;

import com.example.plivo.sample1.dagger2.AppComponent;
import com.example.plivo.sample1.dagger2.DaggerAppComponent;

public class PlivoApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
