package com.adsale.HEATEC.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.MyActivityManager;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.TitleView;
import com.baidu.mobstat.StatService;

public abstract class BaseFragmentActivity extends FragmentActivity {
	private static final String TAG = "BaseFragmentAty";
	private TitleView mTitleView;
	public Context mContext;
	public String mBaiduTJ;
	public String mHallName;
	public String mEventID;
	/**�?术交流会日期*/
	public String mTechnicalDate;
	private String eventName;
	private int mCurrLang;
	private FrameLayout fl;

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
	//	LogUtil.i("BaseFragmentAty", "BaseFragmentAty:"+mContext);
		
		MyActivityManager.add(this);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.frame_container);
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.frame_container, fragment).commit();
		}
		mContext=getApplicationContext();
		
		fl=(FrameLayout) findViewById(R.id.frame_container);
		
		
		mCurrLang = SystemMethod.getCurLanguage(mContext);
		String lang = "";
		if (mCurrLang == 1) {
			lang = "en";
		} else if (mCurrLang == 2) {
			lang = "sc";
		} else {
			lang = "tc";
		}
		
		if(mBaiduTJ!=null){
			eventName = "Page_" + mBaiduTJ + "_" + lang + "_Android";
		}else if(mHallName!=null){
			eventName = "Hall_" + mHallName + "_" + lang + "_Android";
		}else if(mEventID!=null){
			eventName = "Event_" + mEventID + "_" + lang + "_Android";
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		StatService.onPageStart(this, eventName);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		StatService.onPageEnd(this, eventName);
	}
}
