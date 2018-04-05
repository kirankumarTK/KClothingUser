package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sendDeviceId();

    }

    private void sendDeviceId() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        WebServices.getInstance(getApplicationContext(), TAG).sendAppId(ConstantValues.APP_ID, android_id, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    Session.getInstance(SplashActivity.this, TAG).storeAppId(response.getJSONObject("data").getString("appid"));
                    CommonMethod.changeActivityWithParamsText(SplashActivity.this, DashboardActivity.class, "", "");
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonMethod.showLog("test", "on resume called ");
    }
}
