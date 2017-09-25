package com.example.im028.kclothinguser.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProductActivity;
import com.example.im028.kclothinguser.utlity.Constant.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by im028 on 3/7/17.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.backSearch)
    RelativeLayout backSearch;
    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.searchButton)
    RelativeLayout searchButton;
    private String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }


    @OnClick({R.id.backSearch, R.id.searchButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backSearch:
                finish();
                break;
            case R.id.searchButton:
                if (!searchEditText.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showLog(TAG, getIntent().getStringExtra("text") + searchEditText.getText().toString());
                    CommonMethod.changeActivityWithParamsText(SearchActivity.this, ProductActivity.class, getIntent().getStringExtra("text"), searchEditText.getText().toString());
                    finish();
                }
                break;
        }
    }
}
