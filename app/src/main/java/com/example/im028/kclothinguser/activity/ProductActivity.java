package com.example.im028.kclothinguser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.ProductsRecyclerViewAdapter;
import com.example.im028.kclothinguser.common.CommonActivity;
import com.example.im028.kclothinguser.dialog.SortActivity;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.utlity.Constant.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by im028 on 30/6/17.
 */

public class ProductActivity extends CommonActivity {

    @BindView(R.id.procductProgress)
    ProgressBar productProgress;
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.productSort)
    LinearLayout productSort;
    @BindView(R.id.productFilter)
    LinearLayout productFilter;

    private ArrayList<DetailCatergories> productsArrayList = new ArrayList<>();
    private String TAG = ProductActivity.class.getSimpleName();
    private String limit = "10";
    private int paged = 1;
    private String search = "", orderBy = "";
    private ProductsRecyclerViewAdapter productsRecyclerViewAdapter;
    private int SORT = 1;
    private String CatergoryID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_product_layout);
        setCatergories(getIntent().getStringExtra("text"));
        ButterKnife.bind(this);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productsRecyclerViewAdapter = new ProductsRecyclerViewAdapter(this, productsArrayList, productRecyclerView);
        productRecyclerView.setAdapter(productsRecyclerViewAdapter);
        productProgress.setVisibility(View.VISIBLE);
        getProducts(paged, getIntent().getStringExtra("text"), search, orderBy);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setItemViewCacheSize(20);
        productRecyclerView.setDrawingCacheEnabled(true);
        productRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        productsRecyclerViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                productsRecyclerViewAdapter.setLoaded();
                productProgress.setVisibility(View.VISIBLE);
                productsArrayList.add(null);
                productsRecyclerViewAdapter.notifyItemInserted(productsArrayList.size() - 1);
                paged++;
                CatergoryID = getIntent().getStringExtra("text");
                search = getIntent().getStringExtra("text1");
                getProducts(paged, CatergoryID, search, orderBy);

            }
        });


    }

    private void getProducts(int page, String category_id, String search, String orderby) {
        WebServices.getInstance(this, TAG).getProducts(ConstantValues.PRODUCTS, String.valueOf(page), limit, category_id, search, orderby,
                new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        productProgress.setVisibility(View.GONE);
                        productRecyclerView.setVisibility(View.VISIBLE);
                        if (paged >= 2 && productsArrayList.size() > 0) {
                            productsArrayList.remove(productsArrayList.size() - 1);
                            productsRecyclerViewAdapter.notifyItemRemoved(productsArrayList.size());
                            productsRecyclerViewAdapter.setLoaded();
                        }
                        if (response.getString("resultcode").equalsIgnoreCase("200")) {
                            for (int i = 0; i < response.getJSONArray("data").length(); i++)
                                productsArrayList.add(new Gson().fromJson(response.getJSONArray("data").getJSONObject(i).toString(), DetailCatergories.class));
                            productsRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            CommonMethod.showSnackbar(productRecyclerView, response.getString("resultmessage"));
                            productProgress.setVisibility(View.GONE);
                            productRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        CommonMethod.showLogError(TAG, message.toString());
                        CommonMethod.showSnackbar(productProgress, message.toString());
                        productProgress.setVisibility(View.GONE);
                        productRecyclerView.setVisibility(View.VISIBLE);
                    }
                });

    }


    @OnClick({R.id.productRecyclerView, R.id.productSort, R.id.productFilter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.productRecyclerView:
                break;
            case R.id.productSort:
                showSortDialog();
                break;
            case R.id.productFilter:
                break;
        }
    }

    private void showSortDialog() {
        startActivityForResult(new Intent(this, SortActivity.class), SORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SORT) {
            if (resultCode == Activity.RESULT_OK) {
                paged = 1;
                productProgress.setVisibility(View.VISIBLE);
                productsArrayList.clear();
                if (data.getStringExtra("sort").equalsIgnoreCase("New Products"))
                    getProducts(paged, CatergoryID, search, "new");
                if (data.getStringExtra("sort").equalsIgnoreCase("Popular"))
                    getProducts(paged, CatergoryID, search, "popular");
                if (data.getStringExtra("sort").equalsIgnoreCase("low to high"))
                    getProducts(paged, CatergoryID, search, "price");
                if (data.getStringExtra("sort").equalsIgnoreCase("high to low"))
                    getProducts(paged, CatergoryID, search, "price-desc");


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
