package com.almin.mineweibo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.almin.mineweibo.MineWeiboApplication;
import com.almin.mineweibo.fragment.MineWeiboAbstractFragment;


/**
 * Created by Crazy on 2015/6/1.
 */
public class MineWeiboAbstractActivity extends AppCompatActivity {
    private int mContainerId;

    protected void setUpFragmentManagerContainer(int container){
        mContainerId = container;
    }

    protected void setDisplaySize() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        MineWeiboApplication.getInstance().setDisplayHeight(
                displayMetrics.heightPixels);
        MineWeiboApplication.getInstance().setDisplayWidth(
                displayMetrics.widthPixels);
    }

    public void displayFragment(MineWeiboAbstractFragment fragment) {
        addFragmentAndAdd2BackStack(fragment);
    }

    public void toFragment(MineWeiboAbstractFragment fragment){
        replaceFragment(fragment);
    }

    protected void addFragmentAndAdd2BackStack(MineWeiboAbstractFragment fragment) throws IllegalStateException{
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            String tag = fragment.getFragmentTag();
            fragmentManager.beginTransaction().add(mContainerId,fragment,tag).addToBackStack(tag).commit();
        }
    }

    protected void addFragmentAndAdd2BackStackStateLossAllowed(MineWeiboAbstractFragment fragment) throws IllegalStateException{
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            String tag = fragment.getFragmentTag();
            fragmentManager.beginTransaction().add(mContainerId,fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }

    protected void replaceFragmentAndAdd2BackStack(MineWeiboAbstractFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            String tag = fragment.getFragmentTag();
            fragmentManager.beginTransaction().replace(mContainerId,fragment,tag).addToBackStack(tag).commit();
        }
    }

    protected void replaceFragment(MineWeiboAbstractFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            String tag = fragment.getFragmentTag();
            fragmentManager.beginTransaction().replace(mContainerId,fragment,tag).commit();
        }
    }
}
