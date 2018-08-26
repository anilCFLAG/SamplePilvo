package com.plivo.sampleplivo.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.plivo.sampleplivo.R;
import com.plivo.sampleplivo.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.endpoint1, R.id.endpoint2})
    public void onClickEndPoint(View view) {
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra(Constants.EXTRA_USERNAME, view.getId() == R.id.endpoint1 ? Constants.USERNAME_END1 : Constants.USERNAME_END2);
        startActivity(intent);
    }

}
