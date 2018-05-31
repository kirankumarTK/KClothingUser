package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    ImageView splashImage;
    GifImageView animateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = (ImageView) findViewById(R.id.splashImage);
        animateImage = (GifImageView) findViewById(R.id.animateImage);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animateImage.setVisibility(View.GONE);
//                splashImage.setVisibility(View.VISIBLE);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        CommonMethod.clearAllActivity(SplashActivity.this, HomePageActivity.class);
//                    }
//                }, 3000);
//            }
//        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonMethod.clearAllActivity(SplashActivity.this, HomePageActivity.class);
            }
        }, 12000);

    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonMethod.showLog("test", "on resume called ");
    }
}
