package com.example.im028.kclothinguser.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by im028 on 30/6/17.
 */

public class SortActivity extends AppCompatActivity {

    @BindView(R.id.sortNewProducts)
    TextView sortNewProducts;
    @BindView(R.id.sortPopular)
    TextView sortPopular;
    @BindView(R.id.sortLowToHigh)
    TextView sortLowToHigh;
    @BindView(R.id.sortHighToLow)
    TextView sortHighToLow;
    @BindView(R.id.sortDate)
    TextView sortDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_dialog_layout);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.sortNewProducts, R.id.sortPopular, R.id.sortLowToHigh, R.id.sortHighToLow, R.id.sortDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sortNewProducts:
                setResult(RESULT_OK, new Intent().putExtra("sort", sortNewProducts.getText().toString()));
                finish();
                break;
            case R.id.sortPopular:
                setResult(RESULT_OK, new Intent().putExtra("sort", sortPopular.getText().toString()));
                finish();
                break;
            case R.id.sortLowToHigh:
                setResult(RESULT_OK, new Intent().putExtra("sort", sortLowToHigh.getText().toString()));
                finish();
                break;
            case R.id.sortHighToLow:
                setResult(RESULT_OK, new Intent().putExtra("sort", sortHighToLow.getText().toString()));
                finish();
                break;
            case R.id.sortDate:
                setResult(RESULT_OK, new Intent().putExtra("sort", sortDate.getText().toString()));
                finish();
                break;
        }
    }
}
