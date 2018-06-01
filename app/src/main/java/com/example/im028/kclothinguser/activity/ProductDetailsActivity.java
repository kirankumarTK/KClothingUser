package com.example.im028.kclothinguser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnItemClickListener;
import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.DetailCatergoriesRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.StandardSizeRecyclerViewAdapter;
import com.example.im028.kclothinguser.adapter.ViewPageAdapter.ImageSliderAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.dialog.SizeChartActivity;
import com.example.im028.kclothinguser.model.Custom_Size;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.model.ProductDetails;
import com.example.im028.kclothinguser.model.StandardSize;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONArray;
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

public class ProductDetailsActivity extends BackCommonActivity implements OnLoadMoreListener {

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
    @BindView(R.id.productDetailRelatedProducts)
    RecyclerView productDetailRelatedProducts;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.galleryViewPager)
    ViewPager galleryViewPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;
    @BindView(R.id.washCare)
    TextView washCare;
    @BindView(R.id.shipsIn)
    TextView shipsIn;
    @BindView(R.id.transitTime)
    TextView transitTime;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.similar)
    TextView similar;
    @BindView(R.id.whishlist)
    TextView whishlist;
    @BindView(R.id.specialInstruction)
    TextView specialInstruction;
    @BindView(R.id.customSize)
    Button customSize;
    @BindView(R.id.fabric)
    TextView fabric;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.others)
    TextView others;
    @BindView(R.id.others1)
    TextView others1;
    @BindView(R.id.others2)
    TextView others2;
    @BindView(R.id.others3)
    TextView others3;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.addToBag)
    Button addToBag;
    @BindView(R.id.buyNow)
    Button buyNow;
    @BindView(R.id.customSizeValue)
    TextView customSizeValue;
    @BindView(R.id.customValueLayout)
    LinearLayout customValueLayout;
    @BindView(R.id.customSizeValue1)
    TextView customSizeValue1;
    @BindView(R.id.scrolView)
    ScrollView scrolView;

    private ArrayList<StandardSize> standardSizeArrayList = new ArrayList<>();
    private ArrayList<Custom_Size> custom_sizeArrayList = new ArrayList<>();
    private ArrayList<DetailCatergories> detailCategoriesArrayList = new ArrayList<>();
    private ArrayList<ProductDetails> productDetailses = new ArrayList<>();
    private StandardSize standardSize = new StandardSize();
    private String TAG = ProductDetailsActivity.class.getSimpleName();
    private int quantity = 1;
    private StandardSizeRecyclerViewAdapter standardSizeRecyclerViewAdapter;
    private int CUSTOM = 10;
    public Map<String, String> custom_size_map = new HashMap<>();
    private String sizeType = "standeredchecked";
    private int StandardSizePosition = 0;
    private ImageView[] dots;
    private int dotCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.produts_details_page, "Product Details");
        ButterKnife.bind(this);
        setCommonProgressBar();
        getProductDetails(getIntent().getStringExtra("text"));

        galleryViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotCount; i++) {
                    dots[i].setImageResource(R.drawable.viewpager_indicator_dot_unselected);
                }
                dots[position].setImageResource(R.drawable.viewpager_indicator_dot_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void getProductDetails(String product_Id) {
        WebServices.getInstance(this, TAG).getProductsDetails(ConstantValues.PRODUCTS_DETAILS, product_Id, Session.getInstance(ProductDetailsActivity.this, TAG).getApp_id(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    productDetailses.add(new Gson().fromJson(response.getJSONObject("data").toString(), ProductDetails.class));
                    setUpGalleryImage(response.getJSONObject("data").getJSONArray("gallery"));

                    for (int s = 0; s < response.getJSONObject("data").getJSONArray("sizes").length(); s++) {
                        StandardSize standardSize = new StandardSize();
                        standardSize.setSelected(false);
                        standardSize.setSize((String) response.getJSONObject("data").getJSONArray("sizes").get(s));
                        standardSizeArrayList.add(standardSize);
                    }
                    standardSizeRecylerView.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    standardSizeRecyclerViewAdapter = new StandardSizeRecyclerViewAdapter(ProductDetailsActivity.this, standardSizeArrayList, new OnItemClickListener() {
                        @Override
                        public void onItemClick(ArrayList<StandardSize> size, int position) {
                            standardSize = size.get(position);
                            sizeType = "standeredchecked";
                            StandardSizePosition = position;

                        }
                    });
                    standardSizeRecylerView.setAdapter(standardSizeRecyclerViewAdapter);

                    for (int c = 0; c < response.getJSONObject("data").getJSONArray("custom_size").length(); c++) {
                        Custom_Size custom_size = new Custom_Size();
                        custom_size.setLabel(response.getJSONObject("data").getJSONArray("custom_size").getJSONObject(c).getString("label"));
                        custom_size.setName(response.getJSONObject("data").getJSONArray("custom_size").getJSONObject(c).getString("name"));
                        ArrayList customSizeArray = new ArrayList();
                        for (int c1 = 0; c1 < response.getJSONObject("data").getJSONArray("custom_size").getJSONObject(c).getJSONArray("values").length(); c1++) {
                            customSizeArray.add(response.getJSONObject("data").getJSONArray("custom_size").getJSONObject(c).getJSONArray("values").get(c1));
                        }
                        custom_size.setValues(customSizeArray);
                        custom_sizeArrayList.add(custom_size);
                    }

                    productDetailNameTextView.setText(productDetailses.get(0).getProduct_name());
                    productDetailPriceTextView.setText(getResources().getString(R.string.Rs) + productDetailses.get(0).getPrice());

                    washCare.setText("Wash Care : " + productDetailses.get(0).getWash_Care());
                    shipsIn.setText("Ships In : " + productDetailses.get(0).getShips_In());
                    transitTime.setText("Transit Time : " + productDetailses.get(0).getTransit_Time());
                    description.setText(productDetailses.get(0).getProduct_content());
                    fabric.setText("Fabric : " + productDetailses.get(0).getFabric());
                    color.setText("Color : " + productDetailses.get(0).getColour());
                    others.setText(productDetailses.get(0).getOthers());
                    others1.setText(productDetailses.get(0).getOthers1());
                    others2.setText(productDetailses.get(0).getOthers2());
                    others3.setText(productDetailses.get(0).getOthers3());

                    for (int i = 0; i < response.getJSONObject("data").getJSONArray("relatedproduct").length(); i++) {
                        detailCategoriesArrayList.add(new Gson().fromJson(response.getJSONObject("data").getJSONArray("relatedproduct").getJSONObject(i).toString(), DetailCatergories.class));
                    }

                    productDetailRelatedProducts.setLayoutManager(new GridLayoutManager(ProductDetailsActivity.this, 2));
                    productDetailRelatedProducts.setNestedScrollingEnabled(false);
                    productDetailRelatedProducts.setAdapter(new DetailCatergoriesRecyclerViewAdapter(ProductDetailsActivity.this, detailCategoriesArrayList, null, "similarProduct", "", ProductDetailsActivity.this));
                    scrolView.smoothScrollTo(0, 0);
                    hideCommonProgressBar();
                } else {
                    hideCommonProgressBar();
                    CommonMethod.showSnackbar(standardSizeRecylerView, response.getString("resultmessage"), ProductDetailsActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(standardSizeRecylerView, message.toString(), ProductDetailsActivity.this);
            }
        });

    }

    private void setUpGalleryImage(JSONArray jsonArray) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                arrayList.add(jsonArray.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        galleryViewPager.setAdapter(new ImageSliderAdapter(ProductDetailsActivity.this, arrayList));
        dotCount = arrayList.size();
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

    @OnClick({R.id.addToBag, R.id.buyNow, R.id.customSize, R.id.productDetailDecrementImageView, R.id.produvtDetailQuantityTextView, R.id.productDetailIncrementImageView, R.id.productDetailsSizeChart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.customSize:
                startActivityForResult(new Intent(getApplicationContext(), CustomSizeActivity.class).putExtra("custom", custom_sizeArrayList), CUSTOM);
                break;
            case R.id.addToBag:
                if (Session.getInstance(this, TAG).getIsLogin()) {
                    if (sizeType.equalsIgnoreCase("standeredchecked")) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("standardsize", standardSize.getSize());
                        callAddToCartWebService(getIntent().getStringExtra("text"), Session.getInstance(this, TAG).getUserID(), String.valueOf(quantity), sizeType, hashMap, "test");
                    } else
                        callAddToCartWebService(getIntent().getStringExtra("text"), Session.getInstance(this, TAG).getUserID(), String.valueOf(quantity), sizeType, custom_size_map, "test");

                } else {
                    CommonMethod.changeActivity(this, LoginActivity.class);
                }

                break;
            case R.id.buyNow:
                CommonMethod.changeActivity(ProductDetailsActivity.this, ShoppingBagActivity.class);
                break;
        }
    }

    private void callAddToCartWebService(String product_id, String userID, String quantity, String sizeType, Map<String, String> size, String test) {
        WebServices.getInstance(this, TAG).addToBag(ConstantValues.ADD_TO_CART, product_id, userID, quantity, sizeType, size, test, getIntent().getStringExtra("text1"), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    CommonMethod.showSnackbar(addToBag, response.optJSONArray("data").getJSONObject(0).optString("cartmsg"), ProductDetailsActivity.this);
                } else {
                    CommonMethod.showSnackbar(addToBag, response.optString("resultmessage"), ProductDetailsActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CUSTOM) {
            if (resultCode == Activity.RESULT_OK) {
                custom_size_map = (Map<String, String>) data.getSerializableExtra(ConstantValues.custom_size);
                sizeType = "customchecked";
                standardSizeArrayList.get(StandardSizePosition).setSelected(false);
                standardSizeRecyclerViewAdapter.notifyDataSetChanged();

                customValueLayout.setVisibility(View.VISIBLE);
                ArrayList array = new ArrayList();
                customSizeValue.setText("");
                customSizeValue1.setText("");
                for (Map.Entry<String, String> entry : custom_size_map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    CommonMethod.showLogError(TAG, key + "   " + value);
                    array.add(key);

                    if (array.size() != custom_size_map.size() && array.size() % 2 == 0)
                        customSizeValue1.append(key.substring(0, 1).toUpperCase() + key.substring(1).replace("_", " ") + " : " + value + "\n");
                    else if (array.size() != custom_size_map.size() && array.size() % 2 != 0)
                        customSizeValue.append(key.substring(0, 1).toUpperCase() + key.substring(1).replace("_", " ") + " : " + value + "\n");
                    else {
                        if (array.size() % 2 != 0)
                            customSizeValue.append(key.substring(0, 1).toUpperCase() + key.substring(1).replace("_", " ") + " : " + value);
                        else
                            customSizeValue1.append(key.substring(0, 1).toUpperCase() + key.substring(1).replace("_", " ") + " : " + value + "\n");
                    }
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLoadMore() {

    }


}
