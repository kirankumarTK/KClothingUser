package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProductDetailsActivity;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 27/6/17.
 */

public class DetailCatergoriesRecyclerViewAdapter extends RecyclerView.Adapter<DetailCatergoriesRecyclerViewAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<DetailCatergories> detailCatergories;
//    private String category_id;

    public DetailCatergoriesRecyclerViewAdapter(Context context, ArrayList<DetailCatergories> detailCatergories) {
        this.context = context;
        this.detailCatergories = detailCatergories;
//        this.category_id = category_id;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.catergories_detail_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        if (!detailCatergories.get(position).getThumb_image().equals("View More")) {
            try {
                Picasso.with(context)
                        .load(detailCatergories.get(position).getOriginal_image())
                        .placeholder(R.drawable.logo)
                        .fit()
                        .into(holder.catergoreisProductsImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.catergoreisProductsDescriptionTextView.setText(detailCatergories.get(position).getProduct_name());
            holder.catergoreisProductsPriceTextView.setText(context.getResources().getString(R.string.Rs) + detailCatergories.get(position).getPrice());
            holder.catergoreisProductsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonMethod.changeActivityWithParamsText(context, ProductDetailsActivity.class,detailCatergories.get(position).getProduct_id()+"","");
                }
            });
        } else {
            holder.catergoreisProductsImageView.setImageResource(R.drawable.view_more);
            holder.catergoreisProductsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    CommonMethod.changeActivityWithParamsText(context, ProductActivity.class, category_id, "");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return detailCatergories.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.catergoreisProductsImageView)
        ImageView catergoreisProductsImageView;
        @Nullable
        @BindView(R.id.catergoreisProductsDescriptionTextView)
        TextView catergoreisProductsDescriptionTextView;
        @Nullable
        @BindView(R.id.catergoreisProductsPriceTextView)
        TextView catergoreisProductsPriceTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
