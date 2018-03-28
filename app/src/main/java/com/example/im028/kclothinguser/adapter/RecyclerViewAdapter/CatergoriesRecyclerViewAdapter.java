package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProductActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.Slider_Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 27/6/17.
 */

public class CatergoriesRecyclerViewAdapter extends RecyclerView.Adapter<CatergoriesRecyclerViewAdapter.CustomViewHolder> {

    private Context context;
    private List<Slider_Categories> catergoriesArrayList;

    public CatergoriesRecyclerViewAdapter(Context context, List<Slider_Categories> catergoriesArrayList) {
        this.context = context;
        this.catergoriesArrayList = catergoriesArrayList;
        CommonMethod.showLogError("TEST", catergoriesArrayList.toString());
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.catregories_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        try {
            Picasso.with(context)
                    .load(catergoriesArrayList.get(position).getThumbnail())
                    .placeholder(R.drawable.logo)
                    .fit()
                    .into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.title.setText(catergoriesArrayList.get(position).getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivityWithParamsText(context, ProductActivity.class, String.valueOf(catergoriesArrayList.get(position).getId()), "");
            }
        });


    }

    @Override
    public int getItemCount() {
        return catergoriesArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.catergoreisImageView)
        ImageView image;

        @Nullable
        @BindView(R.id.catergoriesTitleTextView)
        TextView title;
        @Nullable
        @BindView(R.id.catergoreisMainLayout)
        RelativeLayout relativeLayout;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
