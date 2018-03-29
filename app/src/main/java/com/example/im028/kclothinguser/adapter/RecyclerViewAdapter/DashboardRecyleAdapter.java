package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.DashBoardModel;

import java.util.ArrayList;

public class DashboardRecyleAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DashBoardModel> arrayList;
    private final int CATERGORY = 1, COLLECTION = 2, FEATURE = 3;

    public DashboardRecyleAdapter(Context context, ArrayList<DashBoardModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CATERGORY:
                return new Catergories(LayoutInflater.from(context).inflate(R.layout.list_item_dashboard_categories, parent, false));
            case COLLECTION:
                return new Collection(LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false));
            case FEATURE:
                return new Feature(LayoutInflater.from(context).inflate(R.layout.list_item_dashboard_feature, parent, false));
            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (arrayList.get(position).getType()) {
            case "Catergory":
                Catergories catergories = (Catergories) holder;
                catergories.catergoreImageView.setBackgroundResource(R.drawable.dresses);
                catergories.catergoreRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                catergories.catergoreRecyclerView.setAdapter(new DashboardCatergoriesAdapter(context, arrayList.get(position).getCategorylist(), R.layout.list_item_dashboard_imageview));
                break;
            case "New Collection":
                Collection collection = (Collection) holder;
                collection.collectionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                collection.collectionRecyclerView.setAdapter(new DashboardCatergoriesAdapter(context, arrayList.get(position).getCategorylist(), R.layout.list_item_dashboard_imagview_width_large));
                break;
            case "Featured Products":
                Feature feature = (Feature) holder;
                feature.feature_title.setText(arrayList.get(position).getType());
                feature.festureRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                feature.festureRecyclerView.setAdapter(new DashboardFeaturedAdapter(context, arrayList.get(position).getFeaturedProductsArrayList()));
                break;

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        CommonMethod.showLog("View", position + "");
        switch (arrayList.get(position).getType()) {
            case "Catergory":
                return CATERGORY;
            case "New Collection":
                return COLLECTION;
            case "Featured Products":
                return FEATURE;
            default:
                return 0;

        }
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

    public class Collection extends RecyclerView.ViewHolder {

        RecyclerView collectionRecyclerView;

        public Collection(View itemView) {
            super(itemView);
            collectionRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

        }
    }

    public class Feature extends RecyclerView.ViewHolder {

        RecyclerView festureRecyclerView;
        Button feature_viewAll;
        TextView feature_title;

        public Feature(View itemView) {
            super(itemView);
            festureRecyclerView = (RecyclerView) itemView.findViewById(R.id.feature_RecyclerView);
            feature_viewAll = (Button) itemView.findViewById(R.id.feature_viewAll);
            feature_title = (TextView) itemView.findViewById(R.id.feature_title);

        }
    }
}
