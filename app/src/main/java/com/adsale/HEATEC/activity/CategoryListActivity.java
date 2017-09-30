package com.adsale.HEATEC.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseFragmentActivity;
import com.adsale.HEATEC.fragment.CategoryListFragment;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;

public class CategoryListActivity extends BaseFragmentActivity {

	public static String TAG = "CategoryListActivity";
	private CategoryListFragment oCategoryListFragment;
	private TitleView mTitleBarView;
	private int mCategoryType = -1; // 1-industry 2-country
	private String mTitle;
	
	@Override
	protected Fragment createFragment() {
		mContext = getApplicationContext();

		String title = getIntent().getStringExtra("Title");
		TitleView mTitleView = (TitleView) findViewById(R.id.titleView1);
		mTitleView.setTitle(title);
		mTitleView.setOnBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Phone")) {
					overridePendingTransition(R.animator.slide_right_enter, R.animator.slide_right_exit);
				} else {
					overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		});
		
		if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		return new CategoryListFragment();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	
}
