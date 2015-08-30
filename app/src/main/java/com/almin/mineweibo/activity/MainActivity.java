package com.almin.mineweibo.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
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
import android.widget.Toast;

import com.almin.mineweibo.R;
import com.almin.mineweibo.fragment.HomeFragment;
import com.almin.mineweibo.fragment.MineWeiboAbstractFragment;
import com.almin.mineweibo.listener.OnFloatingActionButtonListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MineWeiboAbstractActivity {
    private Long mExitTime;
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
        initNavigationView();
        initActionbar();
        initFloatingActionButton();
        setUpFragmentManagerContainer(R.id.container);
        setDisplaySize();


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                /////////////////
//                mActionBarDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
//                mActionBarDrawerToggle.syncState();
                System.out.println("*-BackStackEntryCount---"+getSupportFragmentManager().getBackStackEntryCount());

                //update public view such as floating button.
            }
        });
    }

    private void initFloatingActionButton() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentOnFloatingActionButtonListener != null) {
                    mCurrentOnFloatingActionButtonListener.floatingActionButtonOnClick();
                }
                navigateToHomeFragment();
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
//        final ActionBar actionBar = getSupportActionBar();
//        if(actionBar!=null){
////           actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        showDrawerToggle();
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


//    @Override
//    public void onBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        int entryCount = fragmentManager.getBackStackEntryCount();
//        if (entryCount == 0) {
//            MineWeiboAbstractFragment abstractFragment = (MineWeiboAbstractFragment) fragmentManager
//                    .findFragmentById(R.id.container);
//            if (abstractFragment != null) {
//                abstractFragment.onBackPressed();
//            }
//        } else {
//            if(entryCount == 1){
//                prepareExitApp();
//            }else{
//                MineWeiboAbstractFragment previousFragment = getCurrentFragment();
//                if(previousFragment != null) {
//                    previousFragment.onBackPressed();
//                }
//            }
//        }
//    }

    private void prepareExitApp() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    R.string.message_press_once_more_time_to_exit,
                    Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    public void exitApp() {
        System.exit(0);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);


    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void showDrawerToggle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public void hideDrawerToggle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setHomeButtonEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        }
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


    public void navigateToHomeFragment(){
        displayFragment(new HomeFragment());
    }
}
