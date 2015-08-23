package com.almin.mineweibo.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.almin.mineweibo.activity.MainActivity;
import com.almin.mineweibo.activity.MineWeiboAbstractActivity;
import com.almin.mineweibo.listener.OnFloatingActionButtonListener;
import com.almin.mineweibo.listener.OnUiUpdateControllerListener;

public abstract class MineWeiboAbstractFragment extends Fragment implements OnFloatingActionButtonListener {
	private OnUiUpdateControllerListener mUpdateUiController;
	protected Handler mHandler = new Handler();
	protected boolean mIsPopBackStack = true;
	public abstract String getFragmentTag();

	public static MineWeiboAbstractFragment newInstance(){
		return null;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		MainActivity mainActivity = (MainActivity)activity;
		if(mainActivity != null){
			mUpdateUiController = mainActivity;
			mainActivity.addFloatingActionButtonListeners(this);
			mainActivity.updateCurrentFloatingActionButtonListener();
			ActionBar actionBar = mainActivity.getSupportActionBar();
			if (actionBar != null) {
				if (isActionbarVisible()) {
					actionBar.show();
				} else {
					actionBar.hide();
				}
			}
		}

		onMineWeiboAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = onMineWeiboCreateView(layoutInflater, container,
				savedInstanceState);
		setHasOptionsMenu(true);
		updateActionbarStatus();
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		return view;
	}

	protected View onMineWeiboCreateView(LayoutInflater layoutInflater,
								   ViewGroup container, Bundle savedInstanceState) {
		return super
				.onCreateView(layoutInflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

		onMineWeiboResume();
	}

	protected void onMineWeiboAttach(Activity activity) {
	}

	protected void onMineWeiboResume() {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((MainActivity)getActivity()).removeFloatingActionButtonListeners(this);
		onMineWeiboDestroy();
	}

	protected void onMineWeiboDestroy(){

	}

	//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		super.onCreateOptionsMenu(menu, inflater);
//		for (int i = 0; i < menu.size(); i++) {
//			menu.removeGroup(i);
//		}
//	}

	public boolean onBackPressed() {
		return mIsPopBackStack;
	}

	protected String getActionBarTitle() {
		return "";
	}

	protected void updateActionbarStatus() {
		Activity activity = getActivity();
		if(activity != null){
			ActionBar actionBar = ((MineWeiboAbstractActivity)activity).getSupportActionBar();
			if(actionBar != null){
				actionBar.setTitle(getActionBarTitle());
				actionBar.setLogo(isLogoVisible() ? null: null);
				actionBar.setDisplayHomeAsUpEnabled(isBackButtonVisible());
			}
		}
	}

	protected boolean isActionbarVisible() {
		return true;
	}

	protected boolean isLogoVisible() {
		return true;
	}

	protected boolean isBackButtonVisible() {
		return true;
	}


	protected void showToast(final String msg) {
		final Activity activity = getActivity();
		if (activity != null) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT)
							.show();
				}
			});
		}
	}

}
