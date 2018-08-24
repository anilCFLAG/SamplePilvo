package com.plivo.sampleplivo.ui;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.plivo.sampleplivo.PlivoApplication;
import com.plivo.sampleplivo.R;
import com.plivo.sampleplivo.dagger2.DaggerViewComponent;
import com.plivo.sampleplivo.dagger2.SipModule;
import com.plivo.sampleplivo.dagger2.ViewComponent;
import com.plivo.sampleplivo.dagger2.ViewContextModule;
import com.plivo.sampleplivo.network.Api;
import com.plivo.sampleplivo.network.BaseResponseObj;
import com.plivo.sampleplivo.network.CallPostObj;
import com.plivo.sampleplivo.network.CallResponseObj;
import com.plivo.sampleplivo.sip.Sip;
import com.plivo.sampleplivo.utils.AppUtils;
import com.plivo.sampleplivo.utils.Constants;
import com.plivo.sampleplivo.vm.CallState;
import com.google.gson.JsonElement;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SIP_PERMISSION_REQUEST_CODE = 101;
    private static final String ANSWER_URL = "http://plivodirectdial.herokuapp.com/response/sip/route/?DialMusic=real&CLID=";

    @Inject
    Api api;

    @Inject
    CallState callState;

    @Inject
    AppUtils appUtils;

    @Inject
    Sip sip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerViewComponent.builder()
                .appComponent(((PlivoApplication) getApplication()).getAppComponent())
                .viewContextModule(new ViewContextModule(MainActivity.this))
                .build();
        ((PlivoApplication) getApplication()).getAppComponent()
                .inject(this);

        init();
    }

    private void init() {
        callState.setState(CallState.STATE.CALL_IDLE);

        if (appUtils.checkAndRequestPermissions(this, SIP_PERMISSION_REQUEST_CODE)) {
            sip.init(callListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SIP_PERMISSION_REQUEST_CODE:
                if (grantResults != null &&
                        grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sip.init(callListener);
                }
                break;
        }
    }

    @OnClick(R.id.call)
    public void onCallButtonClick(final View button) {
        button.setEnabled(false);
        switch (callState.getState()) {
            case CALL_IDLE:
                sip.makeCall();
                break;

            case ON_CALL:
                sip.endCall();
                break;
        }
    }

    private Sip.CallListener callListener = new Sip.CallListener() {
        @Override
        public void onCallStarted() {
            callState.setState(CallState.STATE.ON_CALL);
        }

        @Override
        public void onCallEnded() {
            callState.setState(CallState.STATE.CALL_IDLE);
        }
    };

}
