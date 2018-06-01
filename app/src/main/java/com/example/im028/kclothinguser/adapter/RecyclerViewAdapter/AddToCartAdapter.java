package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im028.kclothinguser.Interface.VolleyResponseListerner;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.CartDetails;
import com.example.im028.kclothinguser.utlity.Constant.ConstantValues;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.example.im028.kclothinguser.utlity.webservice.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder> {
    Context context;
    ArrayList<CartDetails> arrayList;
    String TAG = "AddToCartAdapter";


    public AddToCartAdapter(Context context, ArrayList<CartDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.shopping_bag_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.productName.setText(arrayList.get(position).getProduct_name());
        holder.quantity.setText(arrayList.get(position).getQuantity());
        holder.standardTotal.setText("Price : " + arrayList.get(position).getPrice() + "\n" + "\n");

        try {
            Picasso.with(context)
                    .load(arrayList.get(position).getOriginal_image())
                    .into(holder.imageView);
        } catch (Exception e) {
            
        }

        if (!arrayList.get(position).getStandardsize().equalsIgnoreCase("")) {
            holder.standardQtySizeLayout.setVisibility(View.VISIBLE);
            holder.customSizeTextView.setVisibility(View.GONE);
            holder.size.setText(arrayList.get(position).getStandardsize());
        } else {

            holder.standardQtySizeLayout.setVisibility(View.GONE);
            holder.customSizeTextView.setVisibility(View.VISIBLE);

            holder.customSizeTextView.setText("");

            if (!arrayList.get(position).getCustom_hip().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_hip1() + "\n");
            if (!arrayList.get(position).getCustom_chest().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_chest1() + "\n");
            if (!arrayList.get(position).getCustom_waist().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_waist1() + "\n");
            if (!arrayList.get(position).getCustom_sleeve().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_sleeve1() + "\n");
            if (!arrayList.get(position).getCustom_shoulder().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_shoulder1() + "\n");
            if (!arrayList.get(position).getCustom_armhole().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_armhole1() + "\n");
            if (!arrayList.get(position).getCustom_muscle().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getCustom_muscle1() + "\n");
            if (!arrayList.get(position).getGarment_length().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getGarment_length1() + "\n");
            if (!arrayList.get(position).getPant_hip().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_hip1() + "\n");
            if (!arrayList.get(position).getPant_waist().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_waist1() + "\n");
            if (!arrayList.get(position).getPant_legwidth().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_legwidth1() + "\n");
            if (!arrayList.get(position).getPant_length().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_length1() + "\n");
            if (!arrayList.get(position).getPant_inseam().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_inseam1() + "\n");
            if (!arrayList.get(position).getPant_front().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_front1() + "\n");
            if (!arrayList.get(position).getPant_back().equalsIgnoreCase(""))
                holder.customSizeTextView.append(arrayList.get(position).getPant_back1() + "\n");

        }


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServices.getInstance(context, TAG).removeCart(ConstantValues.REMOVE_CART, Session.getInstance(context, TAG).getUserID(), arrayList.get(position).getItem_key(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString("resultcode").equalsIgnoreCase("200")) {
                            Toast.makeText(context, response.optString("data"), Toast.LENGTH_LONG).show();
                            arrayList.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String message, String title) {

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
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.standardQtySizeLayout)
        LinearLayout standardQtySizeLayout;
        @BindView(R.id.customSizeTextView)
        TextView customSizeTextView;
        @BindView(R.id.standardTotal)
        TextView standardTotal;
        @BindView(R.id.remove)
        TextView remove;
        @BindView(R.id.moveToWishlist)
        TextView moveToWishlist;
        @BindView(R.id.productImage)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
