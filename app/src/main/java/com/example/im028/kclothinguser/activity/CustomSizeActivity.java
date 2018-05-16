package com.example.im028.kclothinguser.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.CustomSizeRecyclerViewAdapter;
import com.example.im028.kclothinguser.model.Custom_Size;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 7/14/2017.
 */

public class CustomSizeActivity extends AppCompatActivity {
    @BindView(R.id.customRecyclerView)
    RecyclerView customRecyclerView;
    @BindView(R.id.custom_size_close_button)
    ImageView customSizeCloseButton;
    @BindView(R.id.custom_size_button)
    Button customSizeButton;

    private ArrayList<Custom_Size> custom_sizes;
    private CustomSizeRecyclerViewAdapter customSizeRecyclerViewAdapter;
    private Map<String, Integer> custom_size_map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_size_layout);
        ButterKnife.bind(this);

        custom_sizes = (ArrayList<Custom_Size>) getIntent().getSerializableExtra("custom");
        customRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        for (int i = 0; i < custom_sizes.size(); i++)
            custom_size_map.put(custom_sizes.get(i).getName(), custom_sizes.get(i).getValues().get(0));
        customSizeRecyclerViewAdapter = new CustomSizeRecyclerViewAdapter(this, custom_sizes, custom_size_map);
        customRecyclerView.setAdapter(customSizeRecyclerViewAdapter);
    }

    @OnClick({R.id.custom_size_close_button, R.id.custom_size_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.custom_size_close_button:
                finish();
                break;
            case R.id.custom_size_button:
                setResult(RESULT_OK, new Intent().putExtra(ConstantValues.custom_size, (Serializable) customSizeRecyclerViewAdapter.getCustom_size_value()));
                finish();
                break;
        }
    }
}
