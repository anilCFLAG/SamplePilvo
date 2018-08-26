package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.sip.Sip;
import com.plivo.sampleplivo.ui.MainActivity;
import com.plivo.sampleplivo.vm.IncomingCallReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        SipModule.class
}, dependencies = {
        AppComponent.class
}) @ViewScope
public interface ViewComponent {
    Sip getSip();
    IncomingCallReceiver getCallReceiver();
}
