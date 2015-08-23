package com.almin.mineweibo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.almin.mineweibo.MineWeiboApplication;
import com.almin.mineweibo.R;
import com.almin.mineweibo.fragment.MineWeiboAbstractFragment;
import com.almin.mineweibo.listener.OnUiUpdateControllerListener;


/**
 * Created by Crazy on 2015/6/1.
 */
public class MineWeiboAbstractActivity extends AppCompatActivity implements OnUiUpdateControllerListener {
    private ViewGroup mSpinnerOverlay;
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
            fragmentManager.beginTransaction().replace(mContainerId, fragment, tag).addToBackStack(tag).commit();
        }
    }

    protected void replaceFragment(MineWeiboAbstractFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            String tag = fragment.getFragmentTag();
            fragmentManager.beginTransaction().replace(mContainerId, fragment, tag).commit();
        }
    }

    // just showProgress
    @Override
    public  void showSpinner(LayoutInflater layoutInflater, ViewGroup container) {
        if (mSpinnerOverlay == null) {
            mSpinnerOverlay = (ViewGroup) layoutInflater.inflate(
                    R.layout.overlay_spinner, container, false);
        } else {
            ((ViewGroup) mSpinnerOverlay.getParent())
                    .removeView(mSpinnerOverlay);
        }
        mSpinnerOverlay.setVisibility(View.VISIBLE);
        mSpinnerOverlay.findViewById(R.id.spinner).setVisibility(View.VISIBLE);
        //to stop click pass
        mSpinnerOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        container.addView(mSpinnerOverlay);
    }

    @Override
    public void hideSpinner() {
        if (mSpinnerOverlay != null)
            mSpinnerOverlay.setVisibility(View.GONE);
    }

    @Override
    public void updateOnUiThread(Runnable runnable) {
        if(runnable!=null){
            runOnUiThread(runnable);
        }
    }

    @Override
    public void showToast(final String msg) {
        updateOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), String.format("%s",msg), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
