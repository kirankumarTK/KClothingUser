package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.DashBoardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardCatergoriesAdapter extends RecyclerView.Adapter<DashboardCatergoriesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DashBoardModel.CategorylistEntity> arrayList;

    public DashboardCatergoriesAdapter(Context context, ArrayList<DashBoardModel.CategorylistEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_dashboard_imageview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(arrayList.get(position).getThumbnail())
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
