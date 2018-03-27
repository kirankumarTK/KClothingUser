package com.example.im028.kclothinguser.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.RecyclerViewAdapter.TrunckShowsRecyclerViewAdapter;
import com.example.im028.kclothinguser.common.CommonActivity;
import com.example.im028.kclothinguser.model.Events;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 29/6/17.
 */

public class TrunckShowsActvity extends CommonActivity {
    @BindView(R.id.trunckShowsRecyclerView)
    RecyclerView trunckShowsRecyclerView;
    private ArrayList<Events> eventsArrayList = new ArrayList<>();
    private String TAG = TrunckShowsActvity.class.getSimpleName();
    private int limit = 3;
    private int paged = 1;
    private TrunckShowsRecyclerViewAdapter trunckShowsRecyclerViewAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.trunck_shows_activity);
        ButterKnife.bind(this);
        trunckShowsRecyclerView.setLayoutManager(new LinearLayoutManager(TrunckShowsActvity.this, LinearLayoutManager.VERTICAL, false));
        trunckShowsRecyclerViewAdapter = new TrunckShowsRecyclerViewAdapter(TrunckShowsActvity.this, eventsArrayList, trunckShowsRecyclerView);
        trunckShowsRecyclerView.setAdapter(trunckShowsRecyclerViewAdapter);
        setCommonProgressBar();
        getEvents(String.valueOf(paged), String.valueOf(limit));
        trunckShowsRecyclerViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                eventsArrayList.add(null);
                trunckShowsRecyclerViewAdapter.notifyItemInserted(eventsArrayList.size() - 1);
                paged++;
                getEvents(String.valueOf(paged), String.valueOf(limit));
            }
        });
    }

    private void getEvents(final String page, String limit) {
        WebServices.getInstance(this, TAG).getEvents(ConstantValues.EVENT, page, limit, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (paged >= 2) {
                    eventsArrayList.remove(eventsArrayList.size() - 1);
                    trunckShowsRecyclerViewAdapter.notifyItemRemoved(eventsArrayList.size());
                    trunckShowsRecyclerViewAdapter.setLoaded();
                }
                if (response.getString("resultcode").equalsIgnoreCase("200")) {
                    for (int i = 0; i < response.getJSONArray("data").length(); i++)
                        eventsArrayList.add(new Gson().fromJson(response.getJSONArray("data").getJSONObject(i).toString(), Events.class));
                    trunckShowsRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    CommonMethod.showSnackbar(trunckShowsRecyclerView, response.getString("resultmessage"));
                }
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showLogError(TAG, message.toString());
                CommonMethod.showSnackbar(trunckShowsRecyclerView, message.toString());

            }
        });

    }


}
