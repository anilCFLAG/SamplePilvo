package com.plivo.sampleplivo.sip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;

import java.text.ParseException;

import javax.inject.Inject;

public class Sip {
    private static final String TAG = Sip.class.getSimpleName();

    private static final String USERNAME = "anil180823065556";
    private static final String DOMAIN = "phone.plivo.com";

    private SipManager sipManager;
    private SipProfile sipProfile;
    private SipAudioCall sipCall;

    private CallListener callListener;
    private Context context;

    public interface CallListener {
        void onCallStarted();
        void onCallEnded();
    }

    @Inject
    public Sip(Context context) {
        this.context = context;
    }

    public void init(CallListener listener) {
        callListener = listener;

        sipManager = SipManager.newInstance(context);
        try {
            sipProfile = new SipProfile.Builder(USERNAME, DOMAIN)
                    .setPassword("test1234GL")
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, Intent.FILL_IN_DATA);
        try {
            sipManager.open(sipProfile, pendingIntent, null);
        } catch (SipException e) {
            e.printStackTrace();
        }

        register();
    }

    private void register() {
        try {
            sipManager.setRegistrationListener(sipProfile.getUriString(), new SipRegistrationListener() {
                @Override
                public void onRegistering(String localProfileUri) {
                    Log.d(TAG, "onRegistering " + localProfileUri);
                }

                @Override
                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    Log.d(TAG, "onRegistrationDone " + localProfileUri);
                }

                @Override
                public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                    Log.d(TAG, "onRegistrationFailed " + localProfileUri + "err: " + errorCode + ", " + errorMessage);
                }
            });
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    public void closeProfile() {
        if (sipManager == null) {
            return;
        }

        if (sipProfile != null) {
            try {
                sipManager.close(sipProfile.getUriString());
            } catch (SipException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeCall() {
        Log.d(TAG, "makeCall");
        try {
            sipCall = sipManager.makeAudioCall(sipProfile.getUriString(), "sip:13215786645741657@app.plivo.com", sipListener, 30);
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    public void endCall() {
        Log.d(TAG, "endCall");
        if (sipCall != null) {
            try {
                sipCall.endCall();
            } catch (SipException e) {
                e.printStackTrace();
            }
        }
    }

    private SipAudioCall.Listener sipListener = new SipAudioCall.Listener() {
        @Override
        public void onCallEstablished(SipAudioCall call) {
            Log.d(TAG, "onCallEstablished");
            call.startAudio();
            call.setSpeakerMode(true);
            call.toggleMute();
            if (callListener != null) {
                callListener.onCallStarted();
            }
        }

        @Override
        public void onCallEnded(SipAudioCall call) {
            Log.d(TAG, "onCallEnded");
            if (callListener != null) {
                callListener.onCallEnded();
            }
        }
    };

}
