package com.example.im028.kclothinguser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnItemClickListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.StandardSizeRecyclerViewAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.dialog.CustomSizeActivity;
import com.example.im028.kclothinguser.dialog.SizeChartActivity;
import com.example.im028.kclothinguser.model.Custom_Size;
import com.example.im028.kclothinguser.model.Gallery;
import com.example.im028.kclothinguser.model.ProductDetails;
import com.example.im028.kclothinguser.model.StandardSize;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by im028 on 3/7/17.
 */

public class ProductDetailsActivity extends BackCommonActivity {
    @BindView(R.id.productDetailImageView)
    ImageView productDetailImageView;
    @BindView(R.id.productDetailNameTextView)
    TextView productDetailNameTextView;
    @BindView(R.id.productDetailPriceTextView)
    TextView productDetailPriceTextView;
    @BindView(R.id.productDetailDecrementImageView)
    ImageView productDetailDecrementImageView;
    @BindView(R.id.produvtDetailQuantityTextView)
    TextView produvtDetailQuantityTextView;
    @BindView(R.id.productDetailIncrementImageView)
    ImageView productDetailIncrementImageView;
    @BindView(R.id.standard_size_RecylerView)
    RecyclerView standardSizeRecylerView;
    @BindView(R.id.productDetailsSizeChart)
    LinearLayout productDetailsSizeChart;
    @BindView(R.id.productDetailCustomSizeLayout)
    LinearLayout productDetailCustomSizeLayout;
    @BindView(R.id.productDetailProductsLayout)
    LinearLayout productDetailProductsLayout;
    @BindView(R.id.productDetailRelatedProducts)
    RecyclerView productDetailRelatedProducts;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    private ArrayList<StandardSize> standardSizeArrayList = new ArrayList<>();
    private ArrayList<Custom_Size> custom_sizeArrayList = new ArrayList<>();
    private ArrayList<Gallery> galleryArrayList = new ArrayList<>();
    private ArrayList<ProductDetails> productDetailses = new ArrayList<>();
    private StandardSize standardSize = new StandardSize();
    private String TAG = ProductDetailsActivity.class.getSimpleName();
    private int quantity = 1;
    private StandardSizeRecyclerViewAdapter standardSizeRecyclerViewAdapter;
    private int CUSTOM = 10;
    private Map<String, Integer> custom_size_map = new HashMap<>();
    private String sizeType = "standeredchecked";
    private int StandardSizePosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.produts_details_page);
        ButterKnife.bind(this);
        setCommonProgressBar();
        getProductDetails(getIntent().getStringExtra("text"));
    }


    private void getProductDetails(String product_Id) {

        WebServices.getInstance(this, TAG).getProductsDetails(ConstantValues.PRODUCTS_DETAILS, product_Id, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    for (int i = 0; i < response.getJSONArray("data").length(); i++) {
                        productDetailses.add(new Gson().fromJson(response.getJSONArray("data").getJSONObject(i).toString(), ProductDetails.class));
                        for (int s = 0; s < response.getJSONArray("data").getJSONObject(i).getJSONArray("sizes").length(); s++) {
                            StandardSize standardSize = new StandardSize();
                            standardSize.setSelected(false);
                            standardSize.setSize((String) response.getJSONArray("data").getJSONObject(i).getJSONArray("sizes").get(s));
                            standardSizeArrayList.add(standardSize);
                        }
                        standardSizeRecylerView.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        standardSizeRecyclerViewAdapter = new StandardSizeRecyclerViewAdapter(ProductDetailsActivity.this, standardSizeArrayList, new OnItemClickListener() {
                            @Override
                            public void onItemClick(ArrayList<StandardSize> size, int position) {
                                standardSize = size.get(position);
                                sizeType = "standeredchecked";
                                StandardSizePosition=position;

                            }
                        });
                        standardSizeRecylerView.setAdapter(standardSizeRecyclerViewAdapter);

                        for (int c = 0; c < response.getJSONArray("data").getJSONObject(i).getJSONArray("custom_sizes").length(); c++) {
                            Custom_Size custom_size = new Custom_Size();
                            custom_size.setLabel(response.getJSONArray("data").getJSONObject(i).getJSONArray("custom_sizes").getJSONObject(c).getString("label"));
                            custom_size.setName(response.getJSONArray("data").getJSONObject(i).getJSONArray("custom_sizes").getJSONObject(c).getString("name"));
                            ArrayList customSizeArray = new ArrayList();
                            for (int c1 = 0; c1 < response.getJSONArray("data").getJSONObject(i).getJSONArray("custom_sizes").getJSONObject(c).getJSONArray("values").length(); c1++) {
                                customSizeArray.add(response.getJSONArray("data").getJSONObject(i).getJSONArray("custom_sizes").getJSONObject(c).getJSONArray("values").get(c1));
                            }
                            custom_size.setValues(customSizeArray);
                            custom_sizeArrayList.add(custom_size);
                        }

                        productDetailNameTextView.setText(productDetailses.get(0).getProduct_name());
                        productDetailPriceTextView.setText(productDetailses.get(0).getPrice());
                        try {
                            Picasso.with(getApplicationContext())
                                    .load(productDetailses.get(0).getOriginal_image())
                                    .placeholder(R.drawable.logo)
                                    .into(productDetailImageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    CommonMethod.showSnackbar(standardSizeRecylerView, response.getString("resultmessage"));

            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(standardSizeRecylerView, message.toString());
            }
        });

    }

    @OnClick({R.id.productDetailImageView, R.id.productDetailDecrementImageView, R.id.produvtDetailQuantityTextView, R.id.productDetailIncrementImageView, R.id.productDetailsSizeChart, R.id.productDetailCustomSizeLayout, R.id.productDetailProductsLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.productDetailImageView:
                break;
            case R.id.productDetailDecrementImageView:
                if (quantity != 1)
                    --quantity;
                produvtDetailQuantityTextView.setText(quantity + "");
                break;
            case R.id.produvtDetailQuantityTextView:
                break;
            case R.id.productDetailIncrementImageView:
                quantity++;
                produvtDetailQuantityTextView.setText(quantity + "");
                break;
            case R.id.productDetailsSizeChart:
                CommonMethod.changeActivity(ProductDetailsActivity.this, SizeChartActivity.class);
                break;
            case R.id.productDetailCustomSizeLayout:
                startActivityForResult(new Intent(getApplicationContext(), CustomSizeActivity.class).putExtra("custom", custom_sizeArrayList), CUSTOM);
                break;
            case R.id.productDetailProductsLayout:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CUSTOM) {
            if (resultCode == Activity.RESULT_OK) {
                custom_size_map = (Map<String, Integer>) data.getSerializableExtra(ConstantValues.custom_size);
                sizeType = "custom";
                standardSizeArrayList.get(StandardSizePosition).setSelected(false);
                standardSizeRecyclerViewAdapter.notifyDataSetChanged();

                for (Map.Entry<String, Integer> entry : custom_size_map.entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    CommonMethod.showLogError(TAG, key + "   " + value);
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
