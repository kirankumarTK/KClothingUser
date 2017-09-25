package com.example.im028.kclothinguser.adapter.NavigationAdapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;


/**
 * Created by IM028 on 9/7/16.
 */
public class NavigationDrawerBaseAdapter extends BaseAdapter {
    private Activity activity;
    private String[] menuList = {"Home",
            "Shop",
            "Travelling Trunk",
            "Terms & Conditions",
            "Privacy & Policy",
            "FAQ",
            "Contact Us"};

    public NavigationDrawerBaseAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return menuList.length;
    }

    @Override
    public Object getItem(int i) {
        return menuList[i - 1].toString();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = activity.getLayoutInflater().inflate(R.layout.item_list_navigation_list_view, viewGroup, false);

        TextView textView = (TextView) view.findViewById(R.id.itemListNavigationListViewTextView);
        textView.setText(menuList[i]);
//        imageView.setImageResource(menuIconList[i]);
        return view;
    }
}
