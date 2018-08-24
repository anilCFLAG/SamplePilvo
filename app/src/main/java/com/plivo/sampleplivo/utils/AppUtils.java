package com.plivo.sampleplivo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class AppUtils {

    public String getUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     *
     * @param viewContext
     * @param requestCode
     * @return true if already granted, else false
     */
    public boolean checkAndRequestPermissions(Activity viewContext, int requestCode) {
        if (ContextCompat.checkSelfPermission(viewContext, Manifest.permission.USE_SIP) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(viewContext,
                    new String[]{Manifest.permission.USE_SIP},
                    requestCode);
            return false;
        }
        return true;
    }
}
