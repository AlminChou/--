package com.almin.mineweibo;


import android.widget.Toast;

public class AndroidNetworkConnectivityManager {
	public boolean isNetworkAvailable() {
		return JbsApplication.getInstance().isNetworkAvailable();
	}

	public void showNetworkConnectivityError(){
		Toast.makeText(JbsApplication.getInstance(),"The current network is not available.",Toast.LENGTH_LONG).show();
	}
}
