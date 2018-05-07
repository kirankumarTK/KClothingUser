package com.example.im028.kclothinguser.adapter.expandableListAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ContactActivity;
import com.example.im028.kclothinguser.activity.HomePageActivity;
import com.example.im028.kclothinguser.activity.LoginActivity;
import com.example.im028.kclothinguser.activity.OrderActivity;
import com.example.im028.kclothinguser.activity.ProductCategoryActivity;
import com.example.im028.kclothinguser.activity.ProfileUpdate;
import com.example.im028.kclothinguser.activity.StoreLocatorActivity;
import com.example.im028.kclothinguser.activity.Trunckshow;
import com.example.im028.kclothinguser.common.CommonMethod;
import com.example.im028.kclothinguser.model.CommonNavignationHeader;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;

import java.util.ArrayList;

/**
 * Created by im028 on 12/6/17.
 */

public class CommonNavignationAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<CommonNavignationHeader> commonNavignationHeaders;
    private DrawerLayout mDrawerLayout;

    public CommonNavignationAdapter(Context context, ArrayList<CommonNavignationHeader> commonNavignationHeaders, DrawerLayout mDrawerLayout) {
        this.context = context;
        this.commonNavignationHeaders = commonNavignationHeaders;
        this.mDrawerLayout = mDrawerLayout;
    }


    @Override
    public int getGroupCount() {
        return commonNavignationHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return commonNavignationHeaders.get(groupPosition).getCommonNavignationChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commonNavignationHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commonNavignationHeaders.get(groupPosition).getCommonNavignationChildren().get(childPosition);
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
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

//        if (convertView == null) {
        Context context = this.context;
        convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawerlistitem, null);
//        }

        final ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        final ImageView drop = (ImageView) convertView.findViewById(R.id.drop);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        drop.setVisibility(View.GONE);
        if (commonNavignationHeaders.get(groupPosition).getMenuTitle().equals("Shop") || commonNavignationHeaders.get(groupPosition).getMenuTitle().equals("My account")) {
            txtTitle.setText(commonNavignationHeaders.get(groupPosition).getMenuTitle());
            drop.setVisibility(View.VISIBLE);
            drop.setImageResource(R.drawable.down_arrow);

        } else {
            txtTitle.setText(commonNavignationHeaders.get(groupPosition).getMenuTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayView(commonNavignationHeaders.get(groupPosition).getMenuTitle());
                }
            });
        }
        imgIcon.setImageResource(commonNavignationHeaders.get(groupPosition).getImage());


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sub_menu_layout, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        convertView.startAnimation(animation);

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(commonNavignationHeaders.get(groupPosition).getCommonNavignationChildren().get(childPosition).getImages());
        txtTitle.setText(commonNavignationHeaders.get(groupPosition).getCommonNavignationChildren().get(childPosition).getMenuSubTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(commonNavignationHeaders.get(groupPosition).getCommonNavignationChildren().get(childPosition).getMenuSubTitle());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    private void displayView(String position) {

        switch (position) {
            case "Home":
                mDrawerLayout.closeDrawers();
                changeActivity(HomePageActivity.class);
                break;

            case "My order":
                mDrawerLayout.closeDrawers();
                Intent i = new Intent(context, OrderActivity.class);
                i.putExtra("title", "My order");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                break;

            case "My wishlist":
                mDrawerLayout.closeDrawers();
                Intent i1 = new Intent(context, OrderActivity.class);
                i1.putExtra("title", "My wishlist");
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i1);
                break;

            case "More about you":
                mDrawerLayout.closeDrawers();
                changeActivity(ProfileUpdate.class);
                break;

            case "Dresses":
                mDrawerLayout.closeDrawers();
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "dresses", "");
                break;

            case "Tunics":
                mDrawerLayout.closeDrawers();
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "tunics", "");
                break;

            case "Tops":
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "tops", "");
                mDrawerLayout.closeDrawers();
                break;

            case "Pants":
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "pants", "");
                mDrawerLayout.closeDrawers();
                break;

            case "Jackets and Shrugs":
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "jackets and shrugs", "");
                mDrawerLayout.closeDrawers();
                break;

            case "Shirts":
                CommonMethod.changeActivityWithParamsText(context, ProductCategoryActivity.class, "shirts", "");
                mDrawerLayout.closeDrawers();
                break;

            case "New arrivals":
               /* changeActivity(HomePageActivity.class);
                mDrawerLayout.closeDrawers();*/
                Toast.makeText(context, "Under development", Toast.LENGTH_LONG).show();
                break;

            case "The Travelling Trunk":
                changeActivity(Trunckshow.class);
                mDrawerLayout.closeDrawers();
                break;

            case "Store locator":
                changeActivity(StoreLocatorActivity.class);
                mDrawerLayout.closeDrawers();
                break;

            case "FAQ":
                // new AsyncTaskAtom(CommonNavignationDrawer.this).execute(ConstantValue.AtomUrl2);
                /*changeActivity(HomePageActivity.class);
                mDrawerLayout.closeDrawers();*/
                Toast.makeText(context, "Under development", Toast.LENGTH_LONG).show();
                break;

            case "Contact us":
                changeActivity(ContactActivity.class);
                mDrawerLayout.closeDrawers();
                break;

            case "Returns & Exchange":
               /* changeActivity(HomePageActivity.class);
                mDrawerLayout.closeDrawers();*/
                Toast.makeText(context, "Under development", Toast.LENGTH_LONG).show();
                break;

            case "Log Out":
                Session.getInstance(context, "").logout();
                Intent i2 = new Intent(context, LoginActivity.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i2);
                mDrawerLayout.closeDrawers();
                break;

            case "Login":
                changeActivity(LoginActivity.class);
                mDrawerLayout.closeDrawers();
                break;

           /* case "Write a Review":
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));

                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }

                mDrawerLayout.closeDrawers();
                break;
            case "Share the App":
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

                sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + context.getPackageName());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                mDrawerLayout.closeDrawers();
                break;

*/
            default:
                break;
        }

    }

    protected void changeActivity(Class<?> c) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
