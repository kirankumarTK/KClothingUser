package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.CatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DetailCatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.ViewPageAdapter.ImageSliderAdapter;
import com.example.im028.kclothinguser.common.CommonActivity;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.model.Events;
import com.example.im028.kclothinguser.model.Slider_Categories;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by im028 on 27/6/17.
 */

public class MainPageActivity extends CommonActivity {

    @BindView(R.id.catergoriesReacyclerViewMainPage)
    RecyclerView catergoriesRecyclerView;
    @BindView(R.id.imageSliderMainPage)
    ViewPager imageSlider;
    @BindView(R.id.nextShowMainPage)
    TextView nextShow;
    @BindView(R.id.nextAllMainPage)
    TextView nextAll;
    @BindView(R.id.eventTitleMainPage)
    TextView eventTitle;
    @BindView(R.id.eventDateTitleMainPage)
    TextView eventDateTitle;
    @BindView(R.id.eventLocationTitleMainPage)
    TextView eventLocationTitle;
    @BindView(R.id.eventsViewMainPage)
    LinearLayout eventsView;
    @BindView(R.id.mainScrollView)
    ScrollView mainScrollView;
    @BindView(R.id.newArrivalsMainLayout)
    TextView newArrivalsMainLayout;
    @BindView(R.id.newArrivalsRecyclerView)
    RecyclerView newArrivalsRecyclerView;
    @BindView(R.id.dressesMainLayout)
    TextView dressesMainLayout;
    @BindView(R.id.dressesRecyclerView)
    RecyclerView dressesRecyclerView;
    @BindView(R.id.jacketsMainLayout)
    TextView jacketsMainLayout;
    @BindView(R.id.jacketsRecyclerView)
    RecyclerView jacketsRecyclerView;
    @BindView(R.id.pantsMainLayout)
    TextView pantsMainLayout;
    @BindView(R.id.pantsRecyclerView)
    RecyclerView pantsRecyclerView;
    @BindView(R.id.tunicMainLayout)
    TextView tunicMainLayout;
    @BindView(R.id.tunicRecyclerView)
    RecyclerView tunicRecyclerView;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;


    private String TAG = MainPageActivity.class.getSimpleName();
    private ArrayList<Slider_Categories> catergoriesArrayList = new ArrayList();
    private ArrayList<String> slider = new ArrayList();
    private ArrayList<DetailCatergories> newArrivalCatergoriesArrayList = new ArrayList<>();
    private ArrayList<DetailCatergories> dressesCatergoriesArrayList = new ArrayList<>();
    private ArrayList<DetailCatergories> tunicCatergoriesArrayList = new ArrayList<>();
    private ArrayList<DetailCatergories> jacketCatergoriesArrayList = new ArrayList<>();
    private ArrayList<DetailCatergories> pantsCatergoriesArrayList = new ArrayList<>();
    private ArrayList<Events> eventsArrayList = new ArrayList<>();
    private Slider_Categories catergories;
    int currentPage = 0;
    Timer timer;
    private ImageView[] dots;
    private int dotsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.main_page_layout);
        ButterKnife.bind(this);
        mainScrollView.setVerticalScrollBarEnabled(false);
        mainScrollView.setHorizontalScrollBarEnabled(false);
        setCommonProgressBar();
        mainScrollView.setVisibility(View.GONE);
        setContent();
        setImageSlider();

    }

    private void setContent() {
        WebServices.getInstance(this, TAG).getMainPageDetails(ConstantValues.GET_CATEGORY, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    setCatergories(response.getJSONArray("data").getJSONObject(0).getJSONArray("subcategory"));
                    setUiPageViewController(response.getJSONArray("data").getJSONObject(0).getJSONArray("slider"));
                    getEvents();
                    callProductApi(response.getJSONArray("data").getJSONObject(0).getJSONArray("newarrivals"), newArrivalsRecyclerView, newArrivalCatergoriesArrayList);
                    callProductApi(response.getJSONArray("data").getJSONObject(0).getJSONArray("dresses"), dressesRecyclerView, tunicCatergoriesArrayList);
                    callProductApi(response.getJSONArray("data").getJSONObject(0).getJSONArray("tunics"), tunicRecyclerView, newArrivalCatergoriesArrayList);
                    callProductApi(response.getJSONArray("data").getJSONObject(0).getJSONArray("jackets"), jacketsRecyclerView, jacketCatergoriesArrayList);
                    callProductApi(response.getJSONArray("data").getJSONObject(0).getJSONArray("pants"), pantsRecyclerView, pantsCatergoriesArrayList);
                } else {
                    hideCommonProgressBar();
                    mainScrollView.setVisibility(View.VISIBLE);
                    CommonMethod.showSnackbar(mainScrollView, response.getString("resultmessage"));
                }
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                mainScrollView.setVisibility(View.VISIBLE);
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(mainScrollView, message.toString());

            }
        });
    }

    private void setImageSlider() {
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

        timer = new Timer(); // This will create a new Thread
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

    private void callProductApi(JSONArray jsonArray, final RecyclerView recyclerView, final ArrayList<DetailCatergories> arrayList) {
        arrayList.clear();
        for (int i = 0; i < jsonArray.length(); i++)
            try {
                arrayList.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(), DetailCatergories.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        setViewMore(recyclerView, arrayList);

    }

    private void setViewMore(RecyclerView recyclerView, ArrayList arrayList) {

        DetailCatergories detailCatergories = new DetailCatergories();
        detailCatergories.setThumb_image("View More");
        arrayList.add(detailCatergories);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new DetailCatergoriesRecyclerViewAdapter(MainPageActivity.this, arrayList));
        hideCommonProgressBar();
        mainScrollView.setVisibility(View.VISIBLE);


    }

    private void getEvents() {
        WebServices.getInstance(this, TAG).getEvents(ConstantValues.EVENT, "1", "", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                mainScrollView.setVisibility(View.VISIBLE);
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    for (int i = 0; i < response.getJSONArray("data").length(); i++)
                        eventsArrayList.add(new Gson().fromJson(response.getJSONArray("data").getJSONObject(i).toString(), Events.class));
                    CommonMethod.setTextTexView(eventTitle, response.getJSONArray("data").getJSONObject(0).getString("event_name"));
                    CommonMethod.setTextTexView(eventDateTitle, response.getJSONArray("data").getJSONObject(0).getString("event_startdate"));
                    CommonMethod.setTextTexView(eventLocationTitle, response.getJSONArray("data").getJSONObject(0).getString("event_location"));
                } else {
                    CommonMethod.showSnackbar(mainScrollView, response.getString("resultmessage"));
                }
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                mainScrollView.setVisibility(View.VISIBLE);
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(mainScrollView, message.toString());

            }
        });

    }

    private void setCatergories(JSONArray response) {
        for (int i = 0; i < response.length(); i++)
            try {
                catergoriesArrayList.add(new Gson().fromJson(response.getJSONObject(i).toString(), Slider_Categories.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        catergoriesRecyclerView.setLayoutManager(new LinearLayoutManager(MainPageActivity.this, LinearLayoutManager.HORIZONTAL, false));
        catergoriesRecyclerView.setAdapter(new CatergoriesRecyclerViewAdapter(MainPageActivity.this, catergoriesArrayList));

    }

    private void setUiPageViewController(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++)
            try {
                slider.add(jsonArray.getJSONObject(i).getString("sliderurl"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageResource(R.drawable.viewpager_indicator_dot_selected);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.catergoriesReacyclerViewMainPage, R.id.nextShowMainPage, R.id.nextAllMainPage,
            R.id.newArrivalsMainLayout, R.id.dressesMainLayout, R.id.jacketsMainLayout, R.id.pantsMainLayout, R.id.tunicMainLayout, R.id.eventsViewMainPage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.catergoriesReacyclerViewMainPage:
                break;
            case R.id.nextShowMainPage:
                break;
            case R.id.nextAllMainPage:
                break;
            case R.id.newArrivalsMainLayout:
                break;
            case R.id.dressesMainLayout:
                break;
            case R.id.jacketsMainLayout:
                break;
            case R.id.pantsMainLayout:
                break;
            case R.id.tunicMainLayout:
                break;
            case R.id.eventsViewMainPage:
                changeActivity();
                break;
        }
    }

    private void changeActivity() {
        CommonMethod.changeActivity(this, TrunckShowsActvity.class);
    }

}
