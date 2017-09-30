package com.adsale.HEATEC.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.base.BaseFragmentActivity;
import com.adsale.HEATEC.fragment.WebContentFragment;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;

public class WebContentActivity extends BaseFragmentActivity {
	private static final String TAG = "WebContentActivity";
	private Context mContext;
	private String mDeviceType;

	@Override
	protected Fragment createFragment() {
		mContext = getApplicationContext();
		mDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");

		String title = getIntent().getStringExtra("Title");
		TitleView mTitleView = (TitleView) findViewById(R.id.titleView1);
		mTitleView.setTitle(title);
		mTitleView.setOnBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (App.mPageIndex == 0) {
					LogUtil.i(TAG, "App.mPageIndex1=" + App.mPageIndex);
					finish();
				} else {
					LogUtil.i(TAG, "App.mPageIndex2=" + App.mPageIndex);
					WebContentFragment.goback();
				}
				if (mDeviceType.equals("Phone")) {
					overridePendingTransition(R.animator.slide_right_enter, R.animator.slide_right_exit);
				} else {
					WebContentActivity.this.overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		});

		mBaiduTJ = getIntent().getStringExtra("baiduTJ");

		if (mDeviceType.equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		return new WebContentFragment();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mDeviceType.equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (App.mPageIndex == 0) {
				finish();
			} else {
				WebContentFragment.goback();
				// WebContentFragment.mWebView.goBack();
				// App.mPageIndex--;
				// //
				// WebContentFragment.mWebView.getSettings().setBuiltInZoomControls(false);
				// //
				// WebContentFragment.mWebView.getSettings().setSupportZoom(false);
				//
				// LogUtil.i(TAG, "App.mPageIndex="+App.mPageIndex);
				// WebContentFragment.mWebView.getSettings().setSupportZoom(false);
				// WebContentFragment.mWebView.getSettings().setUseWideViewPort(false);
				// WebContentFragment.mWebView.getSettings().setLoadWithOverviewMode(false);
			}
			if (mDeviceType.equals("Pad")) {
				overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
