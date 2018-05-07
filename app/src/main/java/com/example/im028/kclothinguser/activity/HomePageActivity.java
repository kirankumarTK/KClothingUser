package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.HomeAdapter;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.common.CommonNavignationDrawer;
import com.example.im028.kclothinguser.model.HomeModel;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by karth on 5/3/2018.
 */

public class HomePageActivity extends CommonNavignationDrawer {

    private static final String TAG = HomePageActivity.class.getSimpleName();
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private ArrayList<HomeModel> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.trunck);
        ButterKnife.bind(this);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        callWebservices();
    }

    private void callWebservices() {
        setCommonProgressBar(8);
        WebServices.getInstance(getApplicationContext(), TAG).getHomePage("http://project986.in/kclothing/api/users/homepage/", Session.getInstance(HomePageActivity.this, TAG).getApp_id(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    arrayList.clear();
                    for (int i = 0; i < response.optJSONArray("data").length(); i++) {
                        HomeModel homeModel = new HomeModel();
                        homeModel.setImageurl(response.optJSONArray("data").optJSONObject(i).optString("imageurl"));
                        homeModel.setName(response.optJSONArray("data").optJSONObject(i).optString("name"));
                        arrayList.add(homeModel);
                    }
                    recycle.setAdapter(new HomeAdapter(getApplicationContext(), arrayList));
                }

            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showSnackbar(recycle, message, HomePageActivity.this);
            }
        });
    }
}
