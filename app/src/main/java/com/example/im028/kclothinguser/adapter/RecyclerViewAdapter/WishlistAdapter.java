package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProductDetailsActivity;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.ProductDetails;
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
 * Created by karth on 5/3/2018.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<ProductDetails> arrayList;
    private String TAG = WishlistAdapter.class.getSimpleName();

    public WishlistAdapter(Activity context, ArrayList<ProductDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (arrayList.get(position).getProduct_name() != null)
            holder.name.setText(arrayList.get(position).getProduct_name());

        if (arrayList.get(position).getStock_status() != null && !arrayList.get(position).getStock_status().equalsIgnoreCase(""))
            holder.status.setText(arrayList.get(position).getStock_status());

        if (arrayList.get(position).getThumb_image() != null && !arrayList.get(position).getThumb_image().equalsIgnoreCase(""))
            Picasso.with(context)
                    .load(arrayList.get(position).getOriginal_image())
                    .error(R.drawable.clothing)
                    .into(holder.imageView);

        if (arrayList.get(position).getPrice() != null && !arrayList.get(position).getPrice().equalsIgnoreCase(""))
            holder.amount.setText("\u20b9 " + arrayList.get(position).getPrice());

        holder.moveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivityWithParamsText(context, ProductDetailsActivity.class,
                        arrayList.get(position).getProduct_id() + "", "");
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getInstance(context, TAG).removeWishlist(ConstantValues.REMOVE_WISHLIST, Session.getInstance(context, TAG).getUserID(),
                        String.valueOf(arrayList.get(position).getProduct_id()), new VolleyResponseListerner() {
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                CommonMethod.showSnackbar(holder.itemView, response, context);
                                arrayList.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onError(String message, String title) {
                                CommonMethod.showSnackbar(holder.itemView, message, context);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.orderImageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.orderProductName)
        TextView name;
        @Nullable
        @BindView(R.id.orderStatus)
        TextView status;
        @Nullable
        @BindView(R.id.orderAmount)
        TextView amount;
        @Nullable
        @BindView(R.id.orderMoveCart)
        Button moveCart;
        @Nullable
        @BindView(R.id.orderRemove)
        Button remove;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
