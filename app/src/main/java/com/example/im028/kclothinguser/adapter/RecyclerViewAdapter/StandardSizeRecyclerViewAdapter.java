package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.im028.kclothinguser.Interface.OnItemClickListener;
import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.model.StandardSize;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 7/13/2017.
 */

public class StandardSizeRecyclerViewAdapter extends RecyclerView.Adapter<StandardSizeRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<StandardSize> standardSizeArrayList;
    private int perviousSelected;
    private OnItemClickListener listener;

    public StandardSizeRecyclerViewAdapter(Context context, ArrayList<StandardSize> standardSizeArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.standardSizeArrayList = standardSizeArrayList;
        this.listener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.standard_size_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (standardSizeArrayList.get(position).isSelected())
            holder.title.setTextColor(Color.WHITE);
        else
            holder.title.setTextColor(Color.BLACK);
        holder.title.setSelected(standardSizeArrayList.get(position).isSelected());
        holder.title.setText(standardSizeArrayList.get(position).getSize());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != perviousSelected) {
                    standardSizeArrayList.get(position).setSelected(true);
                    standardSizeArrayList.get(perviousSelected).setSelected(false);
                    perviousSelected = position;
                    listener.onItemClick(standardSizeArrayList, position);
                } else if (position == 0) {
                    standardSizeArrayList.get(position).setSelected(true);
                    perviousSelected = position;
                    listener.onItemClick(standardSizeArrayList, position);
                }
                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return standardSizeArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.standardSize)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
