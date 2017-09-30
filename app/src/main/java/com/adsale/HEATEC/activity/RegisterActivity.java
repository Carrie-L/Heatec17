package com.adsale.HEATEC.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.base.BaseFragmentActivity;
import com.adsale.HEATEC.fragment.RegisterUrlFragment;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;

public class RegisterActivity extends BaseFragmentActivity {
	public static final String TAG = "RegisterActivity";

	private String mDeviceType;

	@Override
	protected Fragment createFragment() {
		mContext = this;
		mDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");

		String title = getIntent().getStringExtra("Title");
		TitleView mTitleView = (TitleView) findViewById(R.id.titleView1);
		mTitleView.setTitle(title);
		mTitleView.setOnBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				SystemMethod.hideSoftKeyboard(mContext);
				finish();
				if (mDeviceType.equals("Pad")) {
					RegisterActivity.this.overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		}); if (mDeviceType.equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		return new RegisterUrlFragment();
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
//			SystemMethod.hideSoftKeyboard(mContext);
//			hideSoftKeyboard();
			finish();
			if (mDeviceType.equals("Pad")) {
				overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	public void hideSoftKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow( getWindow().getDecorView().getWindowToken(), 0);
	}
	
}
