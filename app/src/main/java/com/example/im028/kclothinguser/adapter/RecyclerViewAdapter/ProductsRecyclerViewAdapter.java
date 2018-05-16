package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 30/6/17.
 */

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private ArrayList<DetailCatergories> detailCatergoriesArrayList;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;

    public ProductsRecyclerViewAdapter(Context context, ArrayList<DetailCatergories> detailCatergoriesArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.detailCatergoriesArrayList = detailCatergoriesArrayList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.products_list_item, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            try {
                Picasso.with(context)
                        .load(detailCatergoriesArrayList.get(position).getMedium_image())
                        .placeholder(R.drawable.logo)
                        .into(userViewHolder.catergoreisProductsImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userViewHolder.catergoreisProductsDescriptionTextView.setText(detailCatergoriesArrayList.get(position).getProduct_name());
            userViewHolder.catergoreisProductsPriceTextView.setText(context.getResources().getString(R.string.Rs) + detailCatergoriesArrayList.get(position).getPrice());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return detailCatergoriesArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return detailCatergoriesArrayList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.ProductsImageView)
        ImageView catergoreisProductsImageView;
        @Nullable
        @BindView(R.id.ProductsDescriptionTextView)
        TextView catergoreisProductsDescriptionTextView;
        @Nullable
        @BindView(R.id.ProductsPriceTextView)
        TextView catergoreisProductsPriceTextView;


        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.progressBar1)
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
