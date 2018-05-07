package com.example.im028.kclothinguser.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.wang.avi.AVLoadingIndicatorView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by im028 on 4/7/17.
 */

public class BackCommonActivity extends AppCompatActivity {
    private static String TAG = BackCommonActivity.class.getSimpleName();
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private ImageView back;
    private TextView backPageTitle;
    private String catergories = "";
    private AVLoadingIndicatorView commonProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_back_activity);
        setCatergories("");

        commonProgressBar = (AVLoadingIndicatorView) findViewById(R.id.commonProgressBar);
        toolbar = (Toolbar) findViewById(R.id.backcommonActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back = (ImageView) findViewById(R.id.backCommonActivityBackImageView);
        backPageTitle = (TextView) findViewById(R.id.backPageTitle);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setView(int viewLayout, String title) {
        backPageTitle.setText(title);
        frameLayout = (FrameLayout) findViewById(R.id.bcakcommonActivityFrameLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        frameLayout.addView(activityView);
    }

    public void setCatergories(String catergories) {
        this.catergories = catergories;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setCommonProgressBar() {
        commonProgressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    public void hideCommonProgressBar() {
        commonProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

}
