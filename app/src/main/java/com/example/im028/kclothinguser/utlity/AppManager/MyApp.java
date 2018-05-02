package com.example.im028.kclothinguser.utlity.AppManager;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.utlity.GCM.RegistrationIntentService;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Im033 on 5/9/2017.
 */

public class MyApp extends MultiDexApplication {
    private static MyApp sInstanse;
    private RequestQueue nRequestQueue;

    public MyApp() {

    }

    public static synchronized MyApp getInstanse() {
        return sInstanse;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ek_mukta_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        sInstanse = this;
        startService(new Intent(this, RegistrationIntentService.class));
    }

    public RequestQueue getnRequestQueue() {
        if (nRequestQueue == null) {
            nRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return nRequestQueue;
    }

    public void addToRequestQueue(Request request) {
        getnRequestQueue().add(request);
    }
}
