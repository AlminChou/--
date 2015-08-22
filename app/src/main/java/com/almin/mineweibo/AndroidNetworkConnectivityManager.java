package com.almin.mineweibo;


import android.widget.Toast;

public class AndroidNetworkConnectivityManager {
	public boolean isNetworkAvailable() {
		return MineWeiboApplication.getInstance().isNetworkAvailable();
	}

	public void showNetworkConnectivityError(){
		Toast.makeText(MineWeiboApplication.getInstance(),"The current network is not available.",Toast.LENGTH_LONG).show();
	}
}
