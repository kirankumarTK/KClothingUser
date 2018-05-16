package com.example.im028.kclothinguser.adapter.ViewPageAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.im028.kclothinguser.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by karth on 7/12/2017.
 */

public class ImageSliderAdapter extends PagerAdapter {


    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> sliderList;

    public ImageSliderAdapter(Context context, List<String> sliderList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sliderList = sliderList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.slider_view, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.slider_image_view);
        try {
            Picasso.with(mContext)
                    .load(sliderList.get(position))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
