package com.example.im028.kclothinguser.common;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.ProfileUpdate;
import com.example.im028.kclothinguser.adapter.expandableListAdapter.CommonNavignationAdapter;
import com.example.im028.kclothinguser.dialog.SearchActivity;
import com.example.im028.kclothinguser.model.CommonNavignationChild;
import com.example.im028.kclothinguser.model.CommonNavignationHeader;
import com.example.im028.kclothinguser.utlity.sharedPreferance.Session;
import com.wang.avi.AVLoadingIndicatorView;

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
    private ImageView search;
    private TextView username;

    FrameLayout frameLayout;
    private String catergories = "";
    private AVLoadingIndicatorView commonProgressBar;

    public CommonNavignationDrawer(int layout) {
        this.layout = layout;
    }

    public CommonNavignationDrawer() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_navignation_drawer);
        toolbar = (Toolbar) findViewById(R.id.commonActivityToolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setCatergories("");
        search = (ImageView) findViewById(R.id.searchCommonActivity);
        commonProgressBar = (AVLoadingIndicatorView) findViewById(R.id.avi);
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


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }

        headerView = getLayoutInflater().inflate(R.layout.header_navigation_list_view, null, false);
        username = (TextView) headerView.findViewById(R.id.menuProfileHeaderName);
        ImageView profileedit = (ImageView) headerView.findViewById(R.id.profileEdit);
        profileedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommonNavignationDrawer.this, ProfileUpdate.class));
            }
        });
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonMethod.changeActivityWithParamsText(CommonNavignationDrawer.this, SearchActivity.class, catergories, "");
            }
        });
    }

    public void setView(int layout) {
        frameLayout = (FrameLayout) findViewById(R.id.frame_container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null, false);
        frameLayout.addView(view);
    }

    public void setCommonProgressBar(int frameVisible) {
        commonProgressBar.setVisibility(View.VISIBLE);
        commonProgressBar.show();
        frameLayout.setVisibility(frameVisible);
    }

    public void hideCommonProgressBar() {
        commonProgressBar.setVisibility(View.GONE);
        commonProgressBar.hide();
        frameLayout.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        updateMenu();
//        username.setText(Session.getInstance(this, "").getFirst_name());
    }

    private void updateMenu() {
        commonNavignationHeadersList.clear();

        if (Session.getInstance(this, "").getIsLogin()) {

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

    public void setCatergories(String catergories) {
        this.catergories = catergories;
    }

    private void setMenu(String status) {
        CommonNavignationHeader commonNavignationHeader;

        //heading
        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Home");
        commonNavignationHeader.setImage(R.drawable.home);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("My account");
        commonNavignationHeader.setImage(R.drawable.share);

        //child starts here
        CommonNavignationChild myAccountChild;
        ArrayList<CommonNavignationChild> myAccountChildArrayList = new ArrayList<>();

        myAccountChild = new CommonNavignationChild();
        myAccountChild.setMenuSubTitle("My order");
        myAccountChild.setImages(R.drawable.share);
        myAccountChildArrayList.add(myAccountChild);

        myAccountChild = new CommonNavignationChild();
        myAccountChild.setMenuSubTitle("My wishlist");
        myAccountChild.setImages(R.drawable.share);
        myAccountChildArrayList.add(myAccountChild);

        myAccountChild = new CommonNavignationChild();
        myAccountChild.setMenuSubTitle("More about you");
        myAccountChild.setImages(R.drawable.share);
        myAccountChildArrayList.add(myAccountChild);

        commonNavignationHeader.setCommonNavignationChildren(myAccountChildArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Shop");
        commonNavignationHeader.setImage(R.drawable.shop);

        //child starts here
        CommonNavignationChild commonNavignationChild;
        ArrayList<CommonNavignationChild> commonNavignationChildArrayList = new ArrayList<>();

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Dresses");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Tunics");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Tops");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Pants");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Jackets and Shrugs");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Shirts");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        /*commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Ponchos");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Scarves");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Jewellery");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);

        commonNavignationChild = new CommonNavignationChild();
        commonNavignationChild.setMenuSubTitle("Bags & Belts");
        commonNavignationChild.setImages(R.drawable.share);
        commonNavignationChildArrayList.add(commonNavignationChild);*/


//child end here

        commonNavignationHeader.setCommonNavignationChildren(commonNavignationChildArrayList);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("New arrivals");
        commonNavignationHeader.setImage(R.drawable.newarrivals);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("The Travelling Trunk");
        commonNavignationHeader.setImage(R.drawable.travelling_trunk);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Store locator");
        commonNavignationHeader.setImage(R.drawable.stores);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("FAQs");
        commonNavignationHeader.setImage(R.drawable.help);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Contact us");
        commonNavignationHeader.setImage(R.drawable.phone);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle("Returns & Exchange");
        commonNavignationHeader.setImage(R.drawable.return1);
        commonNavignationHeadersList.add(commonNavignationHeader);

        commonNavignationHeader = new CommonNavignationHeader();
        commonNavignationHeader.setMenuTitle(status);
        commonNavignationHeader.setImage(R.drawable.logout);
        commonNavignationHeadersList.add(commonNavignationHeader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
