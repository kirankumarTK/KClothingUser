package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.CatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DetailCatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.common.CommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.model.Slider_Categories;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductCategoryActivity extends CommonActivity implements OnLoadMoreListener {

    @BindView(R.id.loadMoreProgress)
    ProgressBar loadMoreProgress;
    private String TAG = MainPageActivity.class.getSimpleName();

    @BindView(R.id.catergoryRecyclerViewMainPage)
    RecyclerView catergoryRecyclerViewMainPage;
    @BindView(R.id.catergoryImageView)
    ImageView catergoryImageView;
    @BindView(R.id.productListRecyclerView)
    RecyclerView productListRecyclerView;
    ArrayList<Slider_Categories> categoryLists;
    ArrayList<DetailCatergories> productList = new ArrayList<>();
    private int limit = 20;
    private int paged = 1;
    private DetailCatergoriesRecyclerViewAdapter detailCatergoriesRecyclerViewAdapter;
    Gson gson = new Gson();
    String loadMoreStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_product_category);
        ButterKnife.bind(this);

        setCommonProgressBar();
//        mainScrollView.setVisibility(View.GONE);
        productListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        detailCatergoriesRecyclerViewAdapter = new DetailCatergoriesRecyclerViewAdapter(this, productList, this);
        productListRecyclerView.setAdapter(detailCatergoriesRecyclerViewAdapter);
        productListRecyclerView.setNestedScrollingEnabled(false);
        catergoryRecyclerViewMainPage.setNestedScrollingEnabled(false);
        setContent(true);

    }

    private void setContent(final boolean updateCategory) {
        WebServices.getInstance(this, TAG).getProductCategoryList(ConstantValues.PRODUCT_CATEGORY_LIST, getIntent().getStringExtra("text"), paged, limit, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
//                    loadMoreStatus = response.getJSONObject("resultmessage").getString("productstatus");
                    hideCommonProgressBar();
                    categoryLists = new ArrayList<>();


                    if (updateCategory) {
                        JSONArray jsonArray = response.getJSONObject("data").getJSONArray("categorylist");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            categoryLists.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Slider_Categories.class));
                        }
                        setCategories(categoryLists);
                        setCategoryImage(response.getJSONObject("data").getString("catImage"));
                    }

                    JSONArray productArray = response.getJSONObject("data").getJSONArray("productlist");
                    for (int i = 0; i < productArray.length(); i++) {
                        productList.add(gson.fromJson(productArray.getJSONObject(i).toString(), DetailCatergories.class));
                    }
                    detailCatergoriesRecyclerViewAdapter.notifyItemInserted(productList.size()-1);
                    loadMoreProgress.setVisibility(View.GONE);

                } else {
                    hideCommonProgressBar();
                    CommonMethod.showSnackbar(catergoryImageView, response.getString("resultmessage"), ProductCategoryActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(catergoryImageView, message.toString(), ProductCategoryActivity.this);
            }
        });
    }


    private void setCategoryImage(String imgUrl) {
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.logo).fit().into(catergoryImageView);
    }

    private void setCategories(ArrayList<Slider_Categories> categoryLists) {

        catergoryRecyclerViewMainPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        catergoryRecyclerViewMainPage.setAdapter(new CatergoriesRecyclerViewAdapter(this, categoryLists));

    }

    @OnClick(R.id.catergoryImageView)
    public void onViewClicked() {
    }

    @Override
    public void onLoadMore() {
//        if (!loadMoreStatus.equalsIgnoreCase("over")) {
            setContent(false);
//            loadMoreProgress.setVisibility(View.VISIBLE);
            paged++;
//        }
    }
}
