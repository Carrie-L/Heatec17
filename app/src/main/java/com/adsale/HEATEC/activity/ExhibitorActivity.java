package com.adsale.HEATEC.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseFragmentActivity;
import com.adsale.HEATEC.fragment.ExhibitorNavFragment;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;

public class ExhibitorActivity extends BaseFragmentActivity {
	public static final String TAG = "ExhibitorActivity";
	private FragmentManager fm;
	private FragmentTransaction ft;
	private TitleView titleView;
	private String mTitle;
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
				finish();
				if (mDeviceType.equals("Pad")) {
					overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		}); if (mDeviceType.equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		
		mBaiduTJ=getIntent().getStringExtra("baiduTJ");

		return new ExhibitorNavFragment();
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
			finish();
			if (mDeviceType.equals("Pad")) {
				overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
