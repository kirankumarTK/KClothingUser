package com.example.im028.kclothinguser.adapter.expandableListAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.LoginActivity;
import com.example.im028.kclothinguser.model.CourseMaterialHeaderModel;

import java.util.ArrayList;

/**
 * Created by im028 on 6/6/17.
 */

public class CourseMaterialExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CourseMaterialHeaderModel> courseMaterialHeaderModels;


    public CourseMaterialExpandableAdapter(Context context, ArrayList<CourseMaterialHeaderModel> courseMaterialHeaderModelsList) {
        this.context = context;
        this.courseMaterialHeaderModels = courseMaterialHeaderModelsList;
    }

    @Override
    public int getGroupCount() {
        return courseMaterialHeaderModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return courseMaterialHeaderModels.get(groupPosition).getCourseMaterialChileModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return courseMaterialHeaderModels.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courseMaterialHeaderModels.get(groupPosition).getCourseMaterialChileModels().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.coursematerial_expandable_header, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.coursematerial_expandable_header_textview);
        textView.setText(courseMaterialHeaderModels.get(groupPosition).getMinorCode());
        ExpandableListView expandableListView = (ExpandableListView) parent;
        expandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.coursematerial_expandable_child, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.coursematerial_expandable_child_textview);
        textView.setText(courseMaterialHeaderModels.get(groupPosition).getCourseMaterialChileModels().get(childPosition).getFileName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LoginActivity.class).putExtra("URL",courseMaterialHeaderModels.get(groupPosition).getCourseMaterialChileModels().get(childPosition).getURl()));
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
