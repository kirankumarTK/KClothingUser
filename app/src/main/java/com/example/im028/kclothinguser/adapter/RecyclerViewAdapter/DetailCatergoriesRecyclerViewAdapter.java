package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnLoadMoreListener;
import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProductDetailsActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.DetailCatergories;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by im028 on 27/6/17.
 */

public class DetailCatergoriesRecyclerViewAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public Activity context;
    private ArrayList<DetailCatergories> detailCatergories;
    private String tag;
    private boolean isLoading, flag = true;
    private OnLoadMoreListener onLoadMoreListener;
    String categoryName, TAG = DetailCatergoriesRecyclerViewAdapter.class.getSimpleName();


    public DetailCatergoriesRecyclerViewAdapter(Activity context, ArrayList<DetailCatergories> detailCatergories, NestedScrollView nestedScrollView, String tag, String categoryName, final OnLoadMoreListener onLoadMoreListener) {
        this.context = context;
        this.detailCatergories = detailCatergories;
        this.onLoadMoreListener = onLoadMoreListener;
        this.tag = tag;
        this.categoryName = categoryName;

        if (!tag.equalsIgnoreCase("similarProduct")) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                                scrollY > oldScrollY) {
                            //code to fetch more data for endless scrolling
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
//                        isLoading = true;
                        }
                    }
                }
            });
        }
//
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.catergories_detail_item_list, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder) holder;
            if (tag.equalsIgnoreCase("similarProduct")) {
                Picasso.with(context)
                        .load(detailCatergories.get(position).getOriginal_image())
                        .into(customViewHolder.catergoreisProductsImageView);
            } else {
                try {
                    Picasso.with(context)
                            .load(detailCatergories.get(position).getMedium_image())
                            .into(customViewHolder.catergoreisProductsImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            customViewHolder.catergoreisProductsDescriptionTextView.setText(detailCatergories.get(position).getProduct_name());
            customViewHolder.catergoreisProductsPriceTextView.setText(context.getResources().getString(R.string.Rs) + detailCatergories.get(position).getPrice() + " (incl of tax)");
            customViewHolder.catergoreisProductsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonMethod.changeActivityWithParamsText(context, ProductDetailsActivity.class, detailCatergories.get(position).getProduct_id() + "", categoryName);
                }
            });
            customViewHolder.wishlistImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (flag){
//                        customViewHolder.wishlistImageView.setImageResource(R.drawable.wishlist);
//                        flag=false;
//                    }else {
//                        customViewHolder.wishlistImageView.setImageResource(R.drawable.wishlist_red);
//                        flag=true;
//                    }
                    addToWishlist(holder.itemView, detailCatergories.get(position).product_id, Session.getInstance(context, TAG).getUserID());
                }

            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    private void addToWishlist(View view, int product_id, String userID) {
        WebServices.getInstance(context, TAG).addWishlist(ConstantValues.ADD_WISHLIST, userID, String.valueOf(product_id), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                CommonMethod.showSnackbar(view, response, context);
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showSnackbar(view, message, context);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
//        return detailCatergories.size() == (position+1) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

        return VIEW_TYPE_ITEM;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }


    @Override
    public int getItemCount() {
//        return detailCatergories.size();
        return detailCatergories == null ? 0 : detailCatergories.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.catergoreisProductsImageView)
        ImageView catergoreisProductsImageView;
        @BindView(R.id.wishlistImageView)
        ImageView wishlistImageView;
        @Nullable
        @BindView(R.id.catergoreisProductsDescriptionTextView)
        TextView catergoreisProductsDescriptionTextView;
        @Nullable
        @BindView(R.id.catergoreisProductsPriceTextView)
        TextView catergoreisProductsPriceTextView;
        @Nullable
        @BindView(R.id.cardView)
        CardView cardView;

        public CustomViewHolder(View itemView) {
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
