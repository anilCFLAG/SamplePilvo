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

import com.plivo.sampleplivo.utils.Constants;

import java.text.ParseException;

import javax.inject.Inject;

public class Sip {
    private static final String TAG = Sip.class.getSimpleName();

    private static final String DOMAIN = "phone.plivo.com";
    private static final String SIP_URI = "sip:%s@" + DOMAIN;
    private static final int CALL_TIMEOUT = 30; // seconds

    private SipManager sipManager;
    private SipProfile sipProfile;
    private SipAudioCall currentCall;

    private SipListener callListener;

    private Context context;

    private String outSipUri;

    public interface SipListener {
        void onRegistered(String localProfileUri);
        void onCallStarted();
        void onCallEnded();
        void onRinging();
    }

    @Inject
    public Sip(Context context) {
        this.context = context;
    }

    public void init(SipListener listener, String username) {
        callListener = listener;

        sipManager = SipManager.newInstance(context);
        closeProfile();
        try {
            sipProfile = new SipProfile.Builder(username, DOMAIN)
                    .setPassword("test1234GL")
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_INCOMING_CALL);
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
                        outSipUri = String.format(SIP_URI,
                                localProfileUri.contains(Constants.USERNAME_END1)? Constants.USERNAME_END2 : Constants.USERNAME_END1);
                        if (callListener != null) {
                            callListener.onRegistered(localProfileUri);
                        }
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

    private void closeProfile() {
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
        if (outSipUri == null) {
            return;
        }

        Log.d(TAG, "makeCall " + outSipUri);
        try {
            currentCall = sipManager.makeAudioCall(sipProfile.getUriString(), outSipUri, sipListener, CALL_TIMEOUT);
        } catch (SipException e) {
            e.printStackTrace();
        }
        if (currentCall.getPeerProfile() != null) {
            Log.d(TAG, "profile: " + currentCall.getPeerProfile().getDisplayName() + ", " + currentCall.getPeerProfile().getUserName() + ", " + currentCall.getPeerProfile().getSipDomain());
        }
    }

    public void endCall() {
        Log.d(TAG, "endCall");
        if (currentCall != null) {
            try {
                currentCall.endCall();
            } catch (SipException e) {
                e.printStackTrace();
            }
        }
    }

    public void takeIncomingCall(Intent incomingIntent) {
        Log.d(TAG, "takeIncomingCall");
        try {
            currentCall = sipManager.takeAudioCall(incomingIntent, sipListener);
            currentCall.answerCall(CALL_TIMEOUT);
            currentCall.startAudio();
            currentCall.setSpeakerMode(true);
            if(currentCall.isMuted()) {
                currentCall.toggleMute();
            }
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    private SipAudioCall.Listener sipListener = new SipAudioCall.Listener() {
        @Override
        public void onCallEstablished(SipAudioCall call) {
            Log.d(TAG, "onCallEstablished ");
            call.startAudio();
            call.setSpeakerMode(true);
            call.toggleMute();
            if (callListener != null) {
                callListener.onCallStarted();
            }
        }

        @Override
        public void onCallEnded(SipAudioCall call) {
            Log.d(TAG, "onCallEnded ");
            if (callListener != null) {
                callListener.onCallEnded();
            }
        }

        @Override
        public void onRinging(SipAudioCall call, SipProfile caller) {
            Log.d(TAG, "onCallRinging ");
            try {
                call.answerCall(CALL_TIMEOUT);
            } catch (SipException e) {
                e.printStackTrace();
            }
            if (callListener != null) {
                callListener.onRinging();
            }
        }
    };

    public void destroy() {
        if (currentCall != null) {
            currentCall.close();
        }
        closeProfile();
    }

}
