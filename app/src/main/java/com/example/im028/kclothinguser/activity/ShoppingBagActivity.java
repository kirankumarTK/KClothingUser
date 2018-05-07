package com.example.im028.kclothinguser.activity;

import android.os.Bundle;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.BackCommonActivity;

import butterknife.ButterKnife;

public class ShoppingBagActivity extends BackCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_shopping_bag,"Cart");
        ButterKnife.bind(this);


    }
}
