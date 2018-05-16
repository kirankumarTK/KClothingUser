package com.example.im028.kclothinguser.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by karth on 5/3/2018.
 */

public class TrunckshowAdapter extends RecyclerView.Adapter<TrunckshowAdapter.ViewHolder> {
    private Context context;

    public TrunckshowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_trunck, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.folding_cell)
        FoldingCell foldingCell;
        @Nullable
        @BindView(R.id.trunck_year)
        TextView year;
        @Nullable
        @BindView(R.id.trunck_date)
        TextView date;
        @Nullable
        @BindView(R.id.trunck_date1)
        TextView date1;
        @Nullable
        @BindView(R.id.trunck_dec)
        TextView dec;
        @BindView(R.id.trunck_dec1)
        TextView dec1;
        @Nullable
        @BindView(R.id.trunck_time)
        TextView time;
        @Nullable
        @BindView(R.id.trunck_image)
        ImageView image;
        @Nullable
        @BindView(R.id.trunck_location)
        TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            foldingCell.initialize(100, 1000, Color.WHITE, 2);

            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foldingCell.toggle(false);
                }
            });
        }
    }
}
