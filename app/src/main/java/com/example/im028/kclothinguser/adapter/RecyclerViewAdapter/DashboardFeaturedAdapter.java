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
import com.example.im028.kclothinguser.model.DashBoardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFeaturedAdapter extends RecyclerView.Adapter<DashboardFeaturedAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DashBoardModel.FeaturedProducts> arrayList;

    public DashboardFeaturedAdapter(Context context, ArrayList<DashBoardModel.FeaturedProducts> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_feature_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(arrayList.get(position).getImage())
                .into(holder.imageView);
        holder.name.setText(arrayList.get(position).getProduct_name());
        holder.price.setText("\u20B9 " + arrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.feature_imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.feature_name)
        TextView name;
        @Nullable
        @BindView(R.id.feature_price)
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
