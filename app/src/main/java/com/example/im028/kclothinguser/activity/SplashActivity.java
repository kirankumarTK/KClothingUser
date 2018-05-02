package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonMethod.changeActivityWithParamsText(SplashActivity.this, DashboardActivity.class, "", "");
            }
        }, 3000);

    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonMethod.showLog("test", "on resume called ");
    }
}
