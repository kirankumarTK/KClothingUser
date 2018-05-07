package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 5/4/2018.
 */

public class StoreLocatorActivity extends BackCommonActivity {

    @BindView(R.id.chennaiPhone)
    TextView chennaiPhone;
    @BindView(R.id.chennaiStoreButton)
    Button chennaiStoreButton;
    @BindView(R.id.mumbaiPhone)
    TextView mumbaiPhone;
    @BindView(R.id.mumbaiStoreButton)
    Button mumbaiStoreButton;
    private String lat = "13.043459", lang = "80.256422";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.list_item_store_locator, "Store Locator");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.chennaiPhone, R.id.chennaiStoreButton, R.id.mumbaiPhone, R.id.mumbaiStoreButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chennaiPhone:
                CommonMethod.call(StoreLocatorActivity.this, chennaiPhone.getText().toString());
                break;
            case R.id.chennaiStoreButton:
                String geoCode = "geo:0,0?q=" + lat + ","
                        + lang;
                CommonMethod.googleMap(StoreLocatorActivity.this, geoCode);
                break;
            case R.id.mumbaiPhone:
                CommonMethod.call(StoreLocatorActivity.this, mumbaiPhone.getText().toString());
                break;
            case R.id.mumbaiStoreButton:
                String geoCode1 = "geo:0,0?q=" + lat + ","
                        + lang;
                CommonMethod.googleMap(StoreLocatorActivity.this, geoCode1);
                break;
        }
    }
}
