package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.MyOrderAdapter;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.WishlistAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.MyOrder;
import com.example.im028.kclothinguser.model.ProductDetails;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 5/4/2018.
 */

public class OrderActivity extends BackCommonActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private ArrayList<ProductDetails> arrayList = new ArrayList<>();
    private ArrayList<MyOrder> myOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.trunck, getIntent().getStringExtra("title"));
        ButterKnife.bind(this);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setCommonProgressBar();
        if (getIntent().getStringExtra("url").equalsIgnoreCase(ConstantValues.GET_WISHLIST))
            getWhishlist();
        else
            getOrderList();
    }

    private void getOrderList() {
        WebServices.getInstance(this, TAG).getWishlist(getIntent().getStringExtra("url"),
                Session.getInstance(getApplicationContext(), TAG).getUserID(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        hideCommonProgressBar();
                        if (response.optString("resultcode").equalsIgnoreCase("200")) {
                            for (int i = 0; i < response.optJSONArray("data").length(); i++)
                                myOrders.add(new Gson().fromJson(response.optJSONArray("data").optJSONObject(i).toString(), MyOrder.class));
                            recycle.setAdapter(new MyOrderAdapter(getApplicationContext(), myOrders));

                        } else
                            CommonMethod.showSnackbar(recycle, response, OrderActivity.this);
                    }

                    @Override
                    public void onError(String message, String title) {
                        hideCommonProgressBar();
                        CommonMethod.showSnackbar(recycle, message, OrderActivity.this);
                    }
                });

    }

    private void getWhishlist() {
        WebServices.getInstance(this, TAG).getWishlist(getIntent().getStringExtra("url"),
                Session.getInstance(getApplicationContext(), TAG).getUserID(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        hideCommonProgressBar();
                        if (response.optString("resultcode").equalsIgnoreCase("200")) {
                            for (int i = 0; i < response.optJSONArray("data").length(); i++)
                                arrayList.add(new Gson().fromJson(response.optJSONArray("data").optJSONObject(i).toString(), ProductDetails.class));

                            recycle.setAdapter(new WishlistAdapter(getApplicationContext(), arrayList));
                        } else
                            CommonMethod.showSnackbar(recycle, response, OrderActivity.this);
                    }

                    @Override
                    public void onError(String message, String title) {
                        hideCommonProgressBar();
                        CommonMethod.showSnackbar(recycle, message, OrderActivity.this);
                    }
                });
    }
}
