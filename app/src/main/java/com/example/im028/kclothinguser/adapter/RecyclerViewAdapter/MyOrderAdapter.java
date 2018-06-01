package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.MyOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by karth on 5/3/2018.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MyOrder> arrayList;

    public MyOrderAdapter(Context context, ArrayList<MyOrder> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_myorder, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderNumber.setText("Order Number\n # " + arrayList.get(position).getOrder_number());
        holder.date.setText(arrayList.get(position).getOrder_date());
        holder.status.setText(arrayList.get(position).getOrder_status());
        holder.amount.setText("\u20b9 " + arrayList.get(position).getOrder_total());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.orderNumber)
        TextView orderNumber;
        @Nullable
        @BindView(R.id.orderDate)
        TextView date;
        @Nullable
        @BindView(R.id.orderStatus)
        TextView status;
        @Nullable
        @BindView(R.id.orderQty)
        TextView qty;
        @Nullable
        @BindView(R.id.orderAmount)
        TextView amount;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
