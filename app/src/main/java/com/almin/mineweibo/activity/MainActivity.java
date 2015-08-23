package com.almin.mineweibo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.almin.mineweibo.R;
import com.almin.mineweibo.listener.OnFloatingActionButtonListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MineWeiboAbstractActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FloatingActionButton mFloatingActionButton;
    private OnFloatingActionButtonListener mCurrentOnFloatingActionButtonListener;
    private List<OnFloatingActionButtonListener> mOnFloatingActionButtonListeners = new ArrayList<OnFloatingActionButtonListener>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        initActionbar();
        initNavigationView();
        initFloatingActionButton();
        setDisplaySize();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                /////////////////
//                mActionBarDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
                mActionBarDrawerToggle.syncState();

            }
        });
    }

    private void initFloatingActionButton() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentOnFloatingActionButtonListener != null){
                    mCurrentOnFloatingActionButtonListener.floatingActionButtonOnClick();
                }
            }
        });
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
//           actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFloatingActionButtonListeners(OnFloatingActionButtonListener onFloatingActionButtonListener) {
        if(onFloatingActionButtonListener != null){
            mOnFloatingActionButtonListeners.add(onFloatingActionButtonListener);
        }
    }

    public void removeFloatingActionButtonListeners(OnFloatingActionButtonListener onFloatingActionButtonListener){
        mOnFloatingActionButtonListeners.remove(onFloatingActionButtonListener);
    }

    public void updateCurrentFloatingActionButtonListener(){
        mCurrentOnFloatingActionButtonListener = mOnFloatingActionButtonListeners.get(mOnFloatingActionButtonListeners.size()-1);
    }
}
