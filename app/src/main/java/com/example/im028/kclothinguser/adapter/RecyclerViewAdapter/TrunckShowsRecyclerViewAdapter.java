package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.Events;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 29/6/17.
 */

public class TrunckShowsRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Events> eventsArrayList;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    public TrunckShowsRecyclerViewAdapter(Context context, ArrayList<Events> eventsArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.eventsArrayList = eventsArrayList;

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
            return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.trunck_shows_list_item, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            if (position % 2 == 0) {
                userViewHolder.trunckShowsCardView.setBackgroundResource(R.color.background_gray);
            } else {
                userViewHolder.trunckShowsCardView.setBackgroundResource(R.color.colorPrimary);
            }
            userViewHolder.trunckShowsName.setText(eventsArrayList.get(position).getEvent_name());
            userViewHolder.trunckShowsDateTextView.setText(eventsArrayList.get(position).getEvent_startdate());
            userViewHolder.trunckShowsCityTextView.setText(eventsArrayList.get(position).getEvent_city());
            userViewHolder.trunckShowsVenueTextView.setText(eventsArrayList.get(position).getEvent_venue());
            userViewHolder.trunckShowsLocationTextView.setText(eventsArrayList.get(position).getEvent_location());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return eventsArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.trunckShowsName)
        TextView trunckShowsName;
        @Nullable
        @BindView(R.id.trunckShowsDateTextView)
        TextView trunckShowsDateTextView;
        @Nullable
        @BindView(R.id.trunckShowsCityTextView)
        TextView trunckShowsCityTextView;
        @Nullable
        @BindView(R.id.trunckShowsVenueTextView)
        TextView trunckShowsVenueTextView;
        @Nullable
        @BindView(R.id.trunckShowsLocationTextView)
        TextView trunckShowsLocationTextView;
        @Nullable
        @BindView(R.id.trunckShowsCardView)
        CardView trunckShowsCardView;

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
