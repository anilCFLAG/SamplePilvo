package com.plivo.sampleplivo.vm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class IncomingCallReceiver extends BroadcastReceiver {
    private static final String TAG = IncomingCallReceiver.class.getSimpleName();

    @Inject
    public IncomingCallReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive " + intent);
        EventBus.getDefault().post(intent);
    }
}
