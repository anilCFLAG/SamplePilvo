package com.plivo.sampleplivo.dagger2;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewContextModule {
    private Context context;

    public ViewContextModule(Context viewContext) {
        this.context = viewContext;
    }

    @Provides
    public Context getContext() {
        return context;
    }
}
