package com.almin.mineweibo.listener;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by crazy on 2015/8/22.
 */
public interface OnUiUpdateControllerListener {
    void showSpinner(LayoutInflater layoutInflater,ViewGroup container);
    void hideSpinner();
    void updateOnUiThread(Runnable runnable);
    void showToast(String msg);
    void showDrawerToggle();
    void hideDrawerToggle();
}