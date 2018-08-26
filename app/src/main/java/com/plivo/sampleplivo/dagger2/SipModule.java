package com.plivo.sampleplivo.dagger2;

import android.app.Activity;
import android.content.Context;

import com.plivo.sampleplivo.sip.Sip;
import com.plivo.sampleplivo.vm.IncomingCallReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (includes = {
        ViewContextModule.class
})
public class SipModule {

    @Provides @ViewScope
    public Sip createSip(Context context) {
       return new Sip(context);
    }

    @Provides @ViewScope
    public IncomingCallReceiver createIncomingCallReceiver() {
        return new IncomingCallReceiver();
    }
}
