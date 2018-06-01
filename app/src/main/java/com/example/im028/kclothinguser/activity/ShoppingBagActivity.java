package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.AddToCartAdapter;
import com.example.im028.kclothinguser.common.BackCommonActivity;
import com.example.im028.kclothinguser.model.CartDetails;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingBagActivity extends BackCommonActivity {


    String TAG = ShoppingBagActivity.class.getSimpleName();
    ArrayList<CartDetails> cartDetails = new ArrayList<>();
    Gson gson = new Gson();
    AddToCartAdapter addToCartAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_add_to_cart, "Cart");
        ButterKnife.bind(this);

//        if (getIntent().getStringExtra("text").equalsIgnoreCase("addToBag")) {
//
//        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        WebServices.getInstance(ShoppingBagActivity.this, TAG).displayCart(ConstantValues.DISPLAY_CART, Session.getInstance(ShoppingBagActivity.this, TAG).getUserID(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    for (int i = 0; i < response.optJSONArray("data").length(); i++) {
                        cartDetails.add(gson.fromJson(response.optJSONArray("data").optJSONObject(i).toString(), CartDetails.class));
                    }

                    recyclerView.setAdapter(new AddToCartAdapter(getApplicationContext(), cartDetails));
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });

    }

}
