package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.TrunckshowAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by karth on 5/3/2018.
 */

public class Trunckshow extends BackCommonActivity {

    @BindView(R.id.recycle)
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.trunck,"Travelling Trunk");
        ButterKnife.bind(this);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(new TrunckshowAdapter(this));
    }
}
