package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.OrderAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 5/4/2018.
 */

public class OrderActivity extends BackCommonActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.trunck, getIntent().getStringExtra("title"));
        ButterKnife.bind(this);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle.setAdapter(new OrderAdapter(getApplicationContext()));
    }
}
