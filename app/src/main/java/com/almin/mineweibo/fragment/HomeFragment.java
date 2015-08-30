package com.almin.mineweibo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almin.mineweibo.R;

/**
 * Created by Administrator on 2015/8/30.
 */
public class HomeFragment extends MineWeiboAbstractFragment {

    @Override
    public String getFragmentTag() {
        return null;
    }

    @Override
    protected String getActionBarTitle() {
        return "Home";
    }

    @Override
    protected View onMineWeiboCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = layoutInflater.inflate(R.layout.fragment_home,container,false);

        return rootView;
    }

    @Override
    public boolean isFloatingActionButtonVisible() {
        return false;
    }

    @Override
    public int getFloatingActionButtonColor() {
        return R.color.snackbar_background_color;
    }

    @Override
    public void floatingActionButtonOnClick() {
        System.out.println("*---home   floating button click---");
    }

    @Override
    public int getFloatingActionButtonLogo() {
        return 0;
    }
}
