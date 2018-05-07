package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Im033 on 5/8/2017.
 */

public class ContactActivity extends BackCommonActivity {
    @BindView(R.id.mapImageView)
    ImageView mapImageView;
    @BindView(R.id.mumbaiphone1TextView)
    TextView mumbaiphone1TextView;
    @BindView(R.id.mumbaiphone2TextView)
    TextView mumbaiphone2TextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.mailTextView)
    TextView mailTextView;
    private String TAG = ContactActivity.class.getName();

    private String lat = "13.043459", lang = "80.256422";
    private String contentString = "Has invite you to kaveri";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_contact, "Contact us");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mapImageView, R.id.mumbaiphone1TextView, R.id.mumbaiphone2TextView, R.id.phoneTextView, R.id.mailTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mapImageView:
                String geoCode = "geo:0,0?q=" + lat + ","
                        + lang;
                CommonMethod.googleMap(ContactActivity.this, geoCode);
                break;
            case R.id.mumbaiphone1TextView:
                CommonMethod.call(ContactActivity.this, mumbaiphone1TextView.getText().toString());
                break;
            case R.id.mumbaiphone2TextView:
                CommonMethod.call(ContactActivity.this, mumbaiphone2TextView.getText().toString());
                break;
            case R.id.phoneTextView:
                CommonMethod.call(ContactActivity.this, phoneTextView.getText().toString());
                break;
            case R.id.mailTextView:
                CommonMethod.sendMail(ContactActivity.this, contentString);
                break;
        }
    }
}
