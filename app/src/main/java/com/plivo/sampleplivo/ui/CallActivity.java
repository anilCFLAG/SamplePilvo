package com.plivo.sampleplivo.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.plivo.sampleplivo.PlivoApplication;
import com.plivo.sampleplivo.R;
import com.plivo.sampleplivo.dagger2.DaggerViewComponent;
import com.plivo.sampleplivo.dagger2.ViewContextModule;
import com.plivo.sampleplivo.sip.Sip;
import com.plivo.sampleplivo.ui.custom.CallButton;
import com.plivo.sampleplivo.utils.AppUtils;
import com.plivo.sampleplivo.utils.Constants;
import com.plivo.sampleplivo.vm.IncomingCallReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallActivity extends AppCompatActivity {
    private static final String TAG = CallActivity.class.getSimpleName();
    private static final int SIP_PERMISSION_REQUEST_CODE = 101;
    private static final String ANSWER_URL = "http://plivodirectdial.herokuapp.com/response/sip/route/?DialMusic=real&CLID=";

    @Inject
    AppUtils appUtils;

    @Inject
    Sip sip;

    @Inject
    IncomingCallReceiver incomingCallReceiver;

    @BindView(R.id.editBox)
    EditText editBox;

    @BindView(R.id.callBtn)
    CallButton callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        ButterKnife.bind(this);

        // view component
        DaggerViewComponent.builder()
                .appComponent(((PlivoApplication) getApplication()).getAppComponent())
                .viewContextModule(new ViewContextModule(CallActivity.this))
                .build();

        ((PlivoApplication) getApplication()).getAppComponent()
                .inject(this);

        init();
    }

    @Override
    protected void onDestroy() {
        sip.destroy();
        unregisterReceiver(incomingCallReceiver);
        super.onDestroy();
    }

    /**
     * Initial / Default values can be placed here
     */
    private void init() {
        // check for USE_SIP permission
        if (appUtils.checkAndRequestPermissions(this, SIP_PERMISSION_REQUEST_CODE)) {
            sip.init(callListener, getIntent().getStringExtra(Constants.EXTRA_USERNAME));
        }

        // register call receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_INCOMING_CALL);
        registerReceiver(incomingCallReceiver, filter);
        EventBus.getDefault().register(this);

        // ui defaults
        callButton.setOnStateChangeListener(new CallButton.OnStateChangeListener() {
            @Override
            public void onStateChanged(CallButton.STATE state) {
                callButton.setEnabled(state != CallButton.STATE.NA);
                callButton.setText(state == CallButton.STATE.ON_CALL ? R.string.end_call : R.string.call);
                callButton.setBackgroundColor(ContextCompat.getColor(CallActivity.this,
                        state == CallButton.STATE.NA? android.R.color.darker_gray:
                                state == CallButton.STATE.ON_CALL? android.R.color.holo_red_dark:
                                        android.R.color.holo_green_dark));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SIP_PERMISSION_REQUEST_CODE:
                if (grantResults != null &&
                        grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sip.init(callListener, getIntent().getStringExtra(Constants.EXTRA_USERNAME));
                }
                break;
        }
    }

    @OnClick(R.id.callBtn)
    public void onCallButtonClick(final CallButton button) {
        button.setEnabled(false);
        switch (button.getState()) {
            case IDLE:
                sip.makeCall();
                break;

            case ON_CALL:
                sip.endCall();
                break;
        }
    }

    private Sip.SipListener callListener = new Sip.SipListener() {
        @Override
        public void onRegistered(final String localProfileUri) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callButton.setState(CallButton.STATE.IDLE);
                    editBox.setText(localProfileUri.contains(Constants.USERNAME_END1)? Constants.USERNAME_END2 : Constants.USERNAME_END1);
                }
            });

        }

        @Override
        public void onCallStarted() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callButton.setState(CallButton.STATE.ON_CALL);
                }
            });
        }

        @Override
        public void onCallEnded() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callButton.setState(CallButton.STATE.IDLE);
                }
            });
        }

        @Override
        public void onRinging() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callButton.setState(CallButton.STATE.NA); // disable call
                    // todo: build notification here
                }
            });
        }
    };

    @Subscribe
    public void onEvent(Intent incomingCallIntent) {
        // handle incoming call here
        sip.takeIncomingCall(incomingCallIntent);
    }
}
