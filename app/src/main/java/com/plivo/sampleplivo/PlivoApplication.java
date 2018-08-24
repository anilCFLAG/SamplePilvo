package com.plivo.sampleplivo;

import android.app.Application;

import com.plivo.sampleplivo.dagger2.AppComponent;
import com.plivo.sampleplivo.dagger2.DaggerAppComponent;

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
