package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.CatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DetailCatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.ViewPageAdapter.ImageSliderAdapter;
import com.example.im028.kclothinguser.common.CommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.model.Slider_Categories;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCategoryActivity extends CommonActivity implements OnLoadMoreListener {

    @BindView(R.id.loadMoreProgress)
    ProgressBar loadMoreProgress;
    @BindView(R.id.catergoryRecyclerViewMainPage)
    RecyclerView catergoryRecyclerViewMainPage;
    @BindView(R.id.productListRecyclerView)
    RecyclerView productListRecyclerView;
    @BindView(R.id.mainScrollView)
    NestedScrollView mainScrollView;
    ArrayList<Slider_Categories> categoryLists;
    ArrayList<DetailCatergories> productList = new ArrayList<>();
    Gson gson = new Gson();
    GridLayoutManager manager;
    String category;
    @BindView(R.id.imageSlider)
    ViewPager imageSlider;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;
    @BindView(R.id.sliderLayout)
    RelativeLayout sliderLayout;


    private String TAG = ProductCategoryActivity.class.getSimpleName();
    private int limit = 20;
    private int paged = 1;
    private DetailCatergoriesRecyclerViewAdapter detailCatergoriesRecyclerViewAdapter;
    private int dotCount;
    private ImageView dots[];
    private ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_product_category);
        ButterKnife.bind(this);

        category = getIntent().getStringExtra("text");

        setCommonProgressBar(8);
        manager = new GridLayoutManager(this, 1);
        productListRecyclerView.setLayoutManager(manager);
        detailCatergoriesRecyclerViewAdapter = new DetailCatergoriesRecyclerViewAdapter(this, productList, mainScrollView, "", this);
        productListRecyclerView.setAdapter(detailCatergoriesRecyclerViewAdapter);
        productListRecyclerView.setNestedScrollingEnabled(false);
        setContent(true, category, paged, limit);

        imageSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < dotCount; i++) {
                    dots[i].setImageResource(R.drawable.viewpager_indicator_dot_unselected);
                }
                dots[position].setImageResource(R.drawable.viewpager_indicator_dot_selected);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setContent(final boolean updateCategory, String category, final int page, int limit) {
        WebServices.getInstance(this, TAG).getProductCategoryList(ConstantValues.PRODUCT_CATEGORY_LIST, category, page, limit, Session.getInstance(ProductCategoryActivity.this, TAG).getApp_id(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                sliderLayout.setVisibility(View.VISIBLE);
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    hideCommonProgressBar();
                    categoryLists = new ArrayList<>();

                    if (updateCategory) {
                        JSONArray jsonArray = response.getJSONObject("data").getJSONArray("categorylist");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            categoryLists.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Slider_Categories.class));
                        }
                        setCategories(categoryLists);

                    }
                    if (page == 1) {

                        // setting imageslider data
                        arrayList.clear();
                        setCategoryImage(response.getJSONObject("data").getJSONArray("catImage"));


                    }
                    JSONArray productArray = response.getJSONObject("data").getJSONArray("productlist");
                    for (int i = 0; i < productArray.length(); i++) {
                        productList.add(gson.fromJson(productArray.getJSONObject(i).toString(), DetailCatergories.class));
                    }

                    detailCatergoriesRecyclerViewAdapter.notifyItemInserted(productList.size() - 1);
                    loadMoreProgress.setVisibility(View.GONE);

                } else {
                    hideCommonProgressBar();
                    loadMoreProgress.setVisibility(View.GONE);
                    mainScrollView.setVisibility(View.VISIBLE);
                    CommonMethod.showSnackbar(mainScrollView, response.getString("resultmessage"), ProductCategoryActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {
                loadMoreProgress.setVisibility(View.GONE);
                hideCommonProgressBar();
                mainScrollView.setVisibility(View.VISIBLE);
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(mainScrollView, message.toString(), ProductCategoryActivity.this);
            }
        });
    }


//        Picasso.with(this).load(imgUrl).placeholder(R.drawable.logo).fit().into(catergoryImageView);

    private void setCategoryImage(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                arrayList.add(jsonArray.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        imageSlider.setAdapter(new ImageSliderAdapter(ProductCategoryActivity.this, arrayList));
        dotCount = arrayList.size();
        viewPagerCountDots.removeAllViews();
        dots = new ImageView[dotCount];

        for (int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.viewpager_indicator_dot_unselected);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            viewPagerCountDots.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.viewpager_indicator_dot_selected);
    }


    private void setCategories(ArrayList<Slider_Categories> categoryLists) {

        catergoryRecyclerViewMainPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        catergoryRecyclerViewMainPage.setAdapter(new CatergoriesRecyclerViewAdapter(this, categoryLists, this));

    }


    @Override
    public void onLoadMore() {
        paged++;
        setContent(false, category, paged, limit);
        loadMoreProgress.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCatogries(String category) {
        paged = 1;
        productList.clear();
        this.category = category;
        detailCatergoriesRecyclerViewAdapter.notifyDataSetChanged();
        setCommonProgressBar(0);
        sliderLayout.setVisibility(View.GONE);
        setContent(false, category, paged, limit);
    }


}
