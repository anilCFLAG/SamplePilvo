package com.plivo.sampleplivo.utils;

import android.content.Context;
import android.provider.Settings;

public class AppUtils {

    public String getUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
