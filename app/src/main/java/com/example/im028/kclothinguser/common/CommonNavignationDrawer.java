package com.example.im028.kclothinguser.common;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.adapter.expandableListAdapter.CommonNavignationAdapter;
import com.example.im028.kclothinguser.model.CommonNavignationChild;
import com.example.im028.kclothinguser.model.CommonNavignationHeader;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;

import java.util.ArrayList;

public class CommonNavignationDrawer extends AppCompatActivity {
    private Toolbar toolbar;
    private ExpandableListView mDrawerList;
    private CommonNavignationAdapter commonNavignationAdapter;
    private ArrayList<CommonNavignationHeader> commonNavignationHeadersList = new ArrayList<>();

    int layout;

    private View headerView, footer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int lastExpandedPosition = -1;

    private TextView username;

    public CommonNavignationDrawer(int layout) {
        this.layout = layout;
    }

    public CommonNavignationDrawer() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common_navignation_drawer);
        toolbar = (Toolbar) findViewById(R.id.commonActivityToolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.navList);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                invalidateOptionsMenu();
//                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                invalidateOptionsMenu();
//                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
//
//            @Override
//            public boolean onOptionsItemSelected(MenuItem item) {
//
//
//                if (item != null && item.getItemId() == R.id.btnMyMenu) {
//                    if (mDrawerLayout.isDrawerOpen(GravityCompat.END/*Gravity.RIGHT*/)) {
//                        // mDrawerLayout.closeDrawer(Gravity.RIGHT);
//                        mDrawerLayout.closeDrawer(GravityCompat.END);
//                    } else {
//                        //mDrawerLayout.openDrawer(Gravity.RIGHT);
//                        mDrawerLayout.openDrawer(GravityCompat.END);
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//
//        };


        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }

        headerView = getLayoutInflater().inflate(R.layout.header_navigation_list_view, null, false);
        username = (TextView) headerView.findViewById(R.id.menuProfileHeaderName);


        footer = getLayoutInflater().inflate(R.layout.menu_footer, null, false);
        TextView version = (TextView) footer.findViewById(R.id.appversion);
        mDrawerList.addHeaderView(headerView);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.setText("Version : " + pInfo.versionName);
            mDrawerList.addFooterView(footer);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    protected void changeActivity(Class<?> c) {
        Intent i = new Intent(CommonNavignationDrawer.this, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
/*
        int menuToUse = R.menu.menu_common_navignation_drawer;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToUse, menu);
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        menuItem.setVisible(false);*/
        return true;


        //  getMenuInflater().inflate(R.menu.menu_common_navignation_drawer, menu);
        //return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMenu();
        username.setText(Session.getInstance(this,"").getFirst_name());
    }

    private void updateMenu() {
        commonNavignationHeadersList.clear();

        if (Session.getInstance(this,"").getIsLogin()) {

            setMenu("Log Out");

        } else {

            setMenu("Login");
        }

        commonNavignationAdapter = new CommonNavignationAdapter(CommonNavignationDrawer.this, commonNavignationHeadersList, mDrawerLayout);


        mDrawerList.setAdapter(commonNavignationAdapter);

        mDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    mDrawerList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

    }


    private void setMenu(String status) {
        CommonNavignationHeader commonNavignationHeader;


        //heading
        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Profile");
        commonNavignationHeader.setImage(R.drawable.about);


        //child starts here
        CommonNavignationChild profile;
        ArrayList<CommonNavignationChild> profileArrayList = new ArrayList<>();

        profile = new CommonNavignationChild();
        profile.setMenuSubTitle("Profile");
        profile.setImages(R.drawable.about);
        profileArrayList.add(profile);

        profile = new CommonNavignationChild();
        profile.setMenuSubTitle("Registered course");
        profile.setImages(R.drawable.logo);
        profileArrayList.add(profile);

        profile = new CommonNavignationChild();
        profile.setMenuSubTitle("View INDoS Certificate");
        profile.setImages(R.drawable.logo);
        profileArrayList.add(profile);

//child end here

        commonNavignationHeader.setCommonNavignationChildren(profileArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);


        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Course Booking");
        commonNavignationHeader.setImage(R.drawable.logo);

        //child starts here
        CommonNavignationChild commonNavignationChild;
        ArrayList<CommonNavignationChild> commonNavignationChildArrayList = new ArrayList<>();

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Quick Search");
        commonNavignationChild.setImages(R.drawable.logo);
        commonNavignationChildArrayList.add(commonNavignationChild);


        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Course Details");
        commonNavignationChild.setImages(R.drawable.logo);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Discount Package");
        commonNavignationChild.setImages(R.drawable.logo);
        commonNavignationChildArrayList.add(commonNavignationChild);


        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Cart");
        commonNavignationChild.setImages(R.drawable.cart);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("My Order");
        commonNavignationChild.setImages(R.drawable.logo);
        commonNavignationChildArrayList.add(commonNavignationChild);


//child end here

        commonNavignationHeader.setCommonNavignationChildren(commonNavignationChildArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Features");
        commonNavignationHeader.setImage(R.drawable.logo);

        CommonNavignationChild features;
        ArrayList<CommonNavignationChild> featuresArrayList = new ArrayList<>();

        features = new CommonNavignationChild();
        features.setMenuSubTitle("Course Material");
        features.setImages(R.drawable.logo);
        featuresArrayList.add(features);


        features = new CommonNavignationChild();
        features.setMenuSubTitle("Certification verification");
        features.setImages(R.drawable.logo);
        featuresArrayList.add(features);

        commonNavignationHeader.setCommonNavignationChildren(featuresArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);


        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("About Us");
        commonNavignationHeader.setImage(R.drawable.logo);

        CommonNavignationChild about;
        ArrayList<CommonNavignationChild> aboutArrayList = new ArrayList<>();

        about = new CommonNavignationChild();
        about.setMenuSubTitle("About");
        about.setImages(R.drawable.about);
        aboutArrayList.add(about);

        about = new CommonNavignationChild();
        about.setMenuSubTitle("Gallery");
        about.setImages(R.drawable.logo);
        aboutArrayList.add(about);


        about = new CommonNavignationChild();
        about.setMenuSubTitle("FAQ");
        about.setImages(R.drawable.logo);
        aboutArrayList.add(about);


        about = new CommonNavignationChild();
        about.setMenuSubTitle("Notifications");
        about.setImages(R.drawable.logo);
        aboutArrayList.add(about);

        about = new CommonNavignationChild();
        about.setMenuSubTitle("Terms and Condition");
        about.setImages(R.drawable.logo);
        aboutArrayList.add(about);

        commonNavignationHeader.setCommonNavignationChildren(aboutArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Contact Us");
        commonNavignationHeader.setImage(R.drawable.logo);
        commonNavignationHeadersList.add(commonNavignationHeader);


        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Others");
        commonNavignationHeader.setImage(R.drawable.logo);

        ArrayList<CommonNavignationChild> commonNavignationChildArrayList1 = new ArrayList<>();

        CommonNavignationChild commonNavignationChild1;


        commonNavignationChild1 = new CommonNavignationChild();
        commonNavignationChild1.setMenuSubTitle("Change Password");
        commonNavignationChild1.setImages(R.drawable.logo);
        commonNavignationChildArrayList1.add(commonNavignationChild1);


        commonNavignationChild1 = new CommonNavignationChild();
        commonNavignationChild1.setMenuSubTitle("Forgot Password");
        commonNavignationChild1.setImages(R.drawable.logo);
        commonNavignationChildArrayList1.add(commonNavignationChild1);


        commonNavignationChild1 = new CommonNavignationChild();
        commonNavignationChild1.setMenuSubTitle("Feedback");
        commonNavignationChild1.setImages(R.drawable.logo);
        commonNavignationChildArrayList1.add(commonNavignationChild1);


        commonNavignationHeader.setCommonNavignationChildren(commonNavignationChildArrayList1);
        commonNavignationHeadersList.add(commonNavignationHeader);


        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Settings");
        commonNavignationHeader.setImage(R.drawable.logo);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Write a Review");
        commonNavignationHeader.setImage(R.drawable.logo);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Share the App");
        commonNavignationHeader.setImage(R.drawable.share);
        commonNavignationHeadersList.add(commonNavignationHeader);


        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle(status);
        commonNavignationHeader.setImage(R.drawable.logo);
        commonNavignationHeadersList.add(commonNavignationHeader);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }*/

        return true;
    }
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
