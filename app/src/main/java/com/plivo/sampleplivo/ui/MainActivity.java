package com.plivo.sampleplivo.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.plivo.sampleplivo.PlivoApplication;
import com.plivo.sampleplivo.R;
import com.plivo.sampleplivo.network.Api;
import com.plivo.sampleplivo.network.BaseResponseObj;
import com.plivo.sampleplivo.network.CallPostObj;
import com.plivo.sampleplivo.network.CallResponseObj;
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

    // for demo purposes, keeping here
    private static final String AUTH_ID = "MANZE4ZJU3OGU0ZDMZYW";
    private static final String AUTH_TOKEN = "ZmZlN2FkMTNiZmYxYmQzN2IyZjIxNDE4NTgxZmI3";

    private static final String ANSWER_URL = "http://plivodirectdial.herokuapp.com/response/sip/route/?DialMusic=real&CLID=";

    @Inject
    Api api;

    @Inject
    CallState callState;

    // demo purpose keeping here.
    private String callUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((PlivoApplication) getApplication()).getAppComponent().inject(this);
        callState.setState(CallState.STATE.CALL_IDLE); // init
    }

    @OnClick(R.id.call)
    public void onCallButtonClick(final View button) {
        button.setEnabled(false);
        switch (callState.getState()) {
            case CALL_IDLE:
                api.makeCall(new CallPostObj.Builder()
                                .setTo("919740253357") // todo: need to get number from the keypad
                                .setFrom("911234567890")
                                .setAnswerUrl(ANSWER_URL + UUID.randomUUID().toString())
                                .build(),
                        AUTH_ID)
                        .enqueue(new Callback<CallResponseObj>() {
                            @Override
                            public void onResponse(Call<CallResponseObj> call, Response<CallResponseObj> response) {
                                button.setEnabled(true);
                                button.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark));
                                if (response != null && response.body() != null) {
                                    callUuid = response.body().getRequestUuid();
                                    callState.setState(CallState.STATE.ON_CALL);

                                }
                            }

                            @Override
                            public void onFailure(Call<CallResponseObj> call, Throwable t) {
                                Log.e(TAG, "makeCall failed " + call);
                                button.setEnabled(true);
                                button.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                            }
                        });
                break;

            case ON_CALL:
                api.endCall(AUTH_ID, callUuid)
                        .enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                button.setEnabled(true);
                                button.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                            }

                            @Override
                            public void onFailure(Call<JsonElement> call, Throwable t) {
                                Log.e(TAG, "endCall failed " + call);
                                button.setEnabled(true);
                                button.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark));
                            }
                        });
                break;
        }


    }

}
