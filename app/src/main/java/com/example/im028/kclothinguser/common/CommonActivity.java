package com.example.im028.kclothinguser.common;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.im028.kclothinguser.R;
import com.example.im028.kclothinguser.activity.MainPageActivity;
import com.example.im028.kclothinguser.activity.ProductActivity;
import com.example.im028.kclothinguser.activity.TrunckShowsActvity;
import com.example.im028.kclothinguser.adapter.NavigationAdapter.NavigationDrawerBaseAdapter;
import com.example.im028.kclothinguser.dialog.SearchActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by IM028 on 9/7/16.
 */
public class CommonActivity extends AppCompatActivity {
    private static String TAG = "CommonActivity";

    DrawerLayout commonActivityDrawerLayout;
    private Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    public ListView listView;
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle toggle;
    public NavigationDrawerBaseAdapter navigationDrawerBaseAdapter;
    private View headerView;
    private ImageView search;
    private String catergories = "";
    private ProgressBar commonProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        setCatergories("");

        toolbar = (Toolbar) findViewById(R.id.commonActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        search = (ImageView) findViewById(R.id.searchCommonActivity);
        drawerLayout = (DrawerLayout) findViewById(R.id.commonActivityDrawerLayout);
        commonProgressBar = (ProgressBar) findViewById(R.id.commonProgressBar);
        listView = (ListView) findViewById(R.id.commonActivityFrameLayoutLeftDrawerListView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                invalidateOptionsMenu();
                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                invalidateOptionsMenu();
                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        headerView = getLayoutInflater().inflate(R.layout.menu_header_layout, null, false);
        listView.addHeaderView(headerView);
        listView.setAdapter(navigationDrawerBaseAdapter = new NavigationDrawerBaseAdapter(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonMethod.changeActivityWithParamsText(CommonActivity.this, SearchActivity.class, catergories, "");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    switch (navigationDrawerBaseAdapter.getItem(i).toString()) {
                        case "Home":
                            CommonMethod.changeActivity(CommonActivity.this, MainPageActivity.class);
                            break;
                        case "Travelling Trunk":
                            CommonMethod.changeActivity(CommonActivity.this, TrunckShowsActvity.class);
                            break;
                        case "Shop":
                            CommonMethod.changeActivity(CommonActivity.this, ProductActivity.class);
                            break;


                        default:
                            CommonMethod.showSnackbar(listView, "Coming Soon",CommonActivity.this);
                    }
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setView(int viewLayout) {
        frameLayout = (FrameLayout) findViewById(R.id.commonActivityFrameLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        frameLayout.addView(activityView);
    }

    public void setCommonProgressBar() {
        commonProgressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    public void hideCommonProgressBar() {
        commonProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }


    public void setCatergories(String catergories) {
        this.catergories = catergories;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
