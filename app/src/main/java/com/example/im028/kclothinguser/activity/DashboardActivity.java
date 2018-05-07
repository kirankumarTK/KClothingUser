package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DashboardRecyleAdapter;
import com.example.im028.kclothinguser.adapter.ViewPageAdapter.ImageSliderAdapter;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.common.CommonNavignationDrawer;
import com.example.im028.kclothinguser.model.DashBoardModel;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends CommonNavignationDrawer {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    @BindView(R.id.imageSliderDashboard)
    ViewPager imageSlider;
    @BindView(R.id.recylerViewMain)
    RecyclerView recylerView;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    private ArrayList<DashBoardModel> arrayList = new ArrayList<>();
    private ArrayList<DashBoardModel.CategorylistEntity> categorylist = new ArrayList<>();
    private ArrayList<DashBoardModel.FeaturedProducts> featuredProducts = new ArrayList<>();
    private DashBoardModel dashBoardModel = new DashBoardModel();
    private ArrayList<String> slider = new ArrayList();
    private ImageView[] dots;
    private int dotsCount;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        callWebservices();
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setNestedScrollingEnabled(false);
    }


    private void callWebservices() {
        setCommonProgressBar(8);
        WebServices.getInstance(getApplicationContext(), TAG).getHomePage(ConstantValues.HOME, Session.getInstance(DashboardActivity.this, TAG).getApp_id(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    // setting imageslider data
                    setUiPageViewController(response.optJSONObject("data").optJSONArray("homepageslider"));

                    // calling auto image slide
                    setImageSlider(slider, imageSlider, dots, dotsCount);

                    // setting date for recycler view
                    //catergories
                    arrayList.clear();
                    for (int i = 0; i < response.optJSONObject("data").optJSONArray("categorylist").length(); i++) {
                        DashBoardModel.CategorylistEntity categoryModel = new DashBoardModel.CategorylistEntity();
                        categoryModel.setId(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optInt("id"));
                        categoryModel.setName(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optString("name"));
                        categoryModel.setThumbnail(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optString("thumbnail"));
                        categorylist.add(categoryModel);
                    }

                    dashBoardModel.setType("Catergory");
                    dashBoardModel.setCategorylist(categorylist);
                    featuredProducts = new ArrayList<>();
                    dashBoardModel.setFeaturedProductsArrayList(featuredProducts);
                    arrayList.add(dashBoardModel);

                    // new collection
                    featuredProducts = new ArrayList<>();
                    categorylist = new ArrayList<>();
                    dashBoardModel = new DashBoardModel();
                    for (int i = 0; i < response.optJSONObject("data").optJSONArray("categorylist").length(); i++) {
                        DashBoardModel.CategorylistEntity categoryModel = new DashBoardModel.CategorylistEntity();
                        categoryModel.setId(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optInt("id"));
                        categoryModel.setName(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optString("name"));
                        categoryModel.setThumbnail(response.optJSONObject("data").optJSONArray("categorylist").optJSONObject(i).optString("thumbnail"));
                        categorylist.add(categoryModel);
                    }


                    dashBoardModel.setType("New Collection");
                    dashBoardModel.setCategorylist(categorylist);
                    dashBoardModel.setFeaturedProductsArrayList(featuredProducts);
                    arrayList.add(dashBoardModel);

                    // featured products
                    featuredProducts = new ArrayList<>();
                    categorylist = new ArrayList<>();
                    dashBoardModel = new DashBoardModel();
                    for (int i = 0; i < response.optJSONObject("data").optJSONArray("featuredproducts").length(); i++) {
                        DashBoardModel.FeaturedProducts featuredModel = new DashBoardModel.FeaturedProducts();
                        featuredModel.setId(response.optJSONObject("data").optJSONArray("featuredproducts").optJSONObject(i).optInt("product_id"));
                        featuredModel.setProduct_name(response.optJSONObject("data").optJSONArray("featuredproducts").optJSONObject(i).optString("product_name"));
                        featuredModel.setPrice(response.optJSONObject("data").optJSONArray("featuredproducts").optJSONObject(i).optString("price"));
                        featuredModel.setStock_status(response.optJSONObject("data").optJSONArray("featuredproducts").optJSONObject(i).optString("stock_status"));
                        featuredModel.setImage(response.optJSONObject("data").optJSONArray("featuredproducts").optJSONObject(i).optString("medium_image"));
                        featuredProducts.add(featuredModel);
                    }


                    dashBoardModel.setType("Featured Products");
                    dashBoardModel.setFeaturedProductsArrayList(featuredProducts);
                    dashBoardModel.setCategorylist(categorylist);
                    arrayList.add(dashBoardModel);


                    recylerView.setAdapter(new DashboardRecyleAdapter(DashboardActivity.this, arrayList));
                } else
                    CommonMethod.showSnackbar(imageSlider, response, DashboardActivity.this);
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showSnackbar(imageSlider, message, DashboardActivity.this);
            }
        });
    }

    private void setUiPageViewController(JSONArray list) {

        for (int i = 0; i < list.length(); i++)
            slider.add(list.optString(i));

        imageSlider.setAdapter(new ImageSliderAdapter(getApplicationContext(), slider));
        dotsCount = slider.size();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.viewpager_indicator_dot_unselected);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageResource(R.drawable.viewpager_indicator_dot_selected);
    }

    public void setImageSlider(final ArrayList<String> slider, final ViewPager imageSlider, final ImageView[] dots, final int dotsCount) {
        currentPage = 0;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (slider.size() > 0)
                    if (currentPage < slider.size())
                        imageSlider.setCurrentItem(currentPage++, true);
                    else {
                        currentPage = 0;
                        imageSlider.setCurrentItem(currentPage, true);
                    }
            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);
        imageSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageResource(R.drawable.viewpager_indicator_dot_unselected);
                }
                dots[position].setImageResource(R.drawable.viewpager_indicator_dot_selected);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
