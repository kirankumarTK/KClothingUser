package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.DashBoardModel;

import java.util.ArrayList;

public class DashboardRecyleAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DashBoardModel> arrayList;
    private int CATERGORY = 1;

    public DashboardRecyleAdapter(Context context, ArrayList<DashBoardModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CATERGORY)
            return new Catergories(LayoutInflater.from(context).inflate(R.layout.list_item_dashboard_categories, parent, false));
        else
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (arrayList.get(position).getType().equalsIgnoreCase("Catergory")) {
            Catergories catergories = (Catergories) holder;
            catergories.catergoreImageView.setBackgroundResource(R.drawable.dresses);
            catergories.catergoreRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            catergories.catergoreRecyclerView.setAdapter(new DashboardCatergoriesAdapter(context, arrayList.get(position).getCategorylist()));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getType().equalsIgnoreCase("Catergory"))
            return CATERGORY;
        else
            return 0;
    }

    public class Catergories extends RecyclerView.ViewHolder {

        RecyclerView catergoreRecyclerView;
        ImageView catergoreImageView;

        public Catergories(View itemView) {
            super(itemView);
            catergoreRecyclerView = (RecyclerView) itemView.findViewById(R.id.catergoreRecyclerView);
            catergoreImageView = (ImageView) itemView.findViewById(R.id.catergoreImageView);
        }
    }
}
