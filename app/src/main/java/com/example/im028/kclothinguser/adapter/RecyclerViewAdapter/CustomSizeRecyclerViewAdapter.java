package com.example.im028.kclothinguser.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.CustomSpinnerAdapter;
import com.example.im028.kclothinguser.model.Custom_Size;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 7/14/2017.
 */

public class CustomSizeRecyclerViewAdapter extends RecyclerView.Adapter<CustomSizeRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Custom_Size> custom_sizes;
    private Map<String, Integer> custom_size_value;

    public CustomSizeRecyclerViewAdapter(Context context, ArrayList<Custom_Size> custom_sizes, Map<String, Integer> custom_size_map) {
        this.context = context;
        this.custom_sizes = custom_sizes;
        this.custom_size_value = custom_size_map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_size_spinner, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customSizeLable.setText(custom_sizes.get(position).getLabel());
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, custom_sizes.get(position).getValues());
        holder.customSizeSpinner.setAdapter(customSpinnerAdapter);
        holder.customSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                custom_size_value.put(custom_sizes.get(position).getName(), Integer.valueOf(parent.getItemAtPosition(position1).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Map<String, Integer> getCustom_size_value() {
        return custom_size_value;
    }

    @Override
    public int getItemCount() {
        return custom_sizes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.customSizeSpinner)
        Spinner customSizeSpinner;
        @Nullable
        @BindView(R.id.customSizeLable)
        TextView customSizeLable;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
