package com.example.im028.kclothinguser.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DetailCatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.common.CommonNavignationDrawer;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductCategoryActivity extends CommonNavignationDrawer implements OnLoadMoreListener {


    @BindView(R.id.productListRecyclerView)
    RecyclerView productListRecyclerView;
    @BindView(R.id.mainScrollView)
    NestedScrollView mainScrollView;
    ArrayList<DetailCatergories> productList = new ArrayList<>();
    Gson gson = new Gson();
    GridLayoutManager manager;
    String category, orderby = "";


    AVLoadingIndicatorView loadMoreProgress;
    @BindView(R.id.filterBy)
    LinearLayout filterBy;
    @BindView(R.id.filterColor)
    LinearLayout filterColor;


    private String TAG = ProductCategoryActivity.class.getSimpleName();
    private int limit = 20;
    private int paged = 1;
    private DetailCatergoriesRecyclerViewAdapter detailCatergoriesRecyclerViewAdapter;

    private int SORT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_product_category);
        ButterKnife.bind(this);
        loadMoreProgress = (AVLoadingIndicatorView) findViewById(R.id.loadMoreProgress);
        category = getIntent().getStringExtra("text");
        setCommonProgressBar(8);
        manager = new GridLayoutManager(this, 1);
        productListRecyclerView.setLayoutManager(manager);
        detailCatergoriesRecyclerViewAdapter = new DetailCatergoriesRecyclerViewAdapter(ProductCategoryActivity.this, productList, mainScrollView, "", category,this);
        productListRecyclerView.setAdapter(detailCatergoriesRecyclerViewAdapter);
        productListRecyclerView.setNestedScrollingEnabled(false);
        if (category.equalsIgnoreCase("new_arrivals"))
            setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
        else
            setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);


    }

    public void setContent(String category, final int page, int limit, String url, String orderby) {
        WebServices.getInstance(this, TAG).getProductCategoryList(url, category, page, limit, Session.getInstance(ProductCategoryActivity.this, TAG).getApp_id(),
                orderby, new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        hideCommonProgressBar();

                        if (response.getString("resultcode").equalsIgnoreCase("200")) {
                            hideCommonProgressBar();

                            JSONArray productArray = response.getJSONObject("data").getJSONArray("productlist");
                            for (int i = 0; i < productArray.length(); i++) {
                                productList.add(gson.fromJson(productArray.getJSONObject(i).toString(), DetailCatergories.class));
                            }

                            detailCatergoriesRecyclerViewAdapter.notifyItemInserted(productList.size() - 1);
                            loadMoreProgress.hide();
                            loadMoreProgress.setVisibility(View.GONE);

                        } else {
                            hideCommonProgressBar();
                            loadMoreProgress.hide();
                            loadMoreProgress.setVisibility(View.GONE);
                            mainScrollView.setVisibility(View.VISIBLE);
                            CommonMethod.showSnackbar(mainScrollView, response.getString("resultmessage"), ProductCategoryActivity.this);
                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        loadMoreProgress.hide();
                        loadMoreProgress.setVisibility(View.GONE);
                        hideCommonProgressBar();
                        mainScrollView.setVisibility(View.VISIBLE);
                        CommonMethod.showLogError(TAG, message.toString());
                        CommonMethod.showSnackbar(mainScrollView, message.toString(), ProductCategoryActivity.this);
                    }
                });
    }


    @Override
    public void onLoadMore() {
        paged++;
        if (category.equalsIgnoreCase("new_arrivals"))
            setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
        else
            setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);

        loadMoreProgress.setVisibility(View.VISIBLE);
        loadMoreProgress.show();

    }


    public void showResult() {
        final Dialog result = new Dialog(this);
        result.setContentView(R.layout.sort_dialog_layout);

        TextView sortNewProducts = (TextView) result.findViewById(R.id.sortNewProducts);
        TextView sortPopular = (TextView) result.findViewById(R.id.sortPopular);
        TextView sortLowToHigh = (TextView) result.findViewById(R.id.sortLowToHigh);
        TextView sortHighToLow = (TextView) result.findViewById(R.id.sortHighToLow);
        ImageView closeDialog = (ImageView) result.findViewById(R.id.closeDialog);

        Window window = result.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        result.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        result.show();

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.dismiss();
            }
        });
        sortNewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                detailCatergoriesRecyclerViewAdapter.notifyDataSetChanged();
                orderby = "new";
                if (category.equalsIgnoreCase("new_arrivals"))
                    setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
                else
                    setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);
                result.dismiss();
            }
        });
        sortPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                detailCatergoriesRecyclerViewAdapter.notifyDataSetChanged();
                orderby = "popular";
                if (category.equalsIgnoreCase("new_arrivals"))
                    setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
                else
                    setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);
                result.dismiss();
            }
        });
        sortLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                detailCatergoriesRecyclerViewAdapter.notifyDataSetChanged();
                orderby = "price";
                if (category.equalsIgnoreCase("new_arrivals"))
                    setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
                else
                    setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);
                result.dismiss();
            }
        });
        sortHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                detailCatergoriesRecyclerViewAdapter.notifyDataSetChanged();
                orderby = "price-desc";
                if (category.equalsIgnoreCase("new_arrivals"))
                    setContent("", paged, limit, ConstantValues.NEW_ARRIVALS, orderby);
                else
                    setContent(category, paged, limit, ConstantValues.PRODUCT_CATEGORY_LIST, orderby);
                result.dismiss();
            }
        });
    }


    @OnClick({R.id.filterBy, R.id.filterColor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.filterBy:
                showResult();
                break;
            case R.id.filterColor:
                break;
        }
    }
}
