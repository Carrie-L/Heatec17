package com.adsale.HEATEC.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.database.ScheduleInfoDBHelper;
import com.adsale.HEATEC.database.model.clsScheduleInfo;
import com.adsale.HEATEC.view.SchedulePagerItem;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

public class SchedulePagerADT extends PagerAdapter {

	private List<List<clsScheduleInfo>> mDateSchedulList;
	private Context mContext;
	private OnClickListener btnAddClickListener;
	private OnItemClickListener mItemClickListener;

	public SchedulePagerADT(Context context, OnClickListener btnAddClickListener, OnItemClickListener mItemClickListener) {
		super();
		mContext = context;
		this.btnAddClickListener = btnAddClickListener;
		this.mItemClickListener = mItemClickListener;
		initData();

	}

	public void initData() {
		mDateSchedulList = new ArrayList<List<clsScheduleInfo>>();
		ScheduleInfoDBHelper oDbHelper = new ScheduleInfoDBHelper(mContext);
		List<clsScheduleInfo> mocolScheduleInfo = oDbHelper.getScheduleInfoList("");

		String startTime1 = "2017-10-10";
		String startTime2 = "2017-10-11";
		String startTime3 = "2017-10-12";

		List<clsScheduleInfo> scheduleList1 = new ArrayList<clsScheduleInfo>();
		List<clsScheduleInfo> scheduleList2 = new ArrayList<clsScheduleInfo>();
		List<clsScheduleInfo> scheduleList3 = new ArrayList<clsScheduleInfo>();

		for (int i = 0; i < mocolScheduleInfo.size(); i++) {
			clsScheduleInfo curSschedule = mocolScheduleInfo.get(i);
			String curStartTime = curSschedule.getStartTime().substring(0, 10);
			if (startTime1.equals(curStartTime)) {
				scheduleList1.add(curSschedule);
			} else if (startTime2.equals(curStartTime)) {
				scheduleList2.add(curSschedule);
			} else if (startTime3.equals(curStartTime)) {
				scheduleList3.add(curSschedule);
			}
		}
		mDateSchedulList.add(scheduleList1);
		mDateSchedulList.add(scheduleList2);
		mDateSchedulList.add(scheduleList3);
	}

	@Override
	public int getCount() {
		return mDateSchedulList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		SchedulePagerItem oSchedulePagerItem = (SchedulePagerItem) object;
		oSchedulePagerItem.DestroyView();
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		SchedulePagerItem oSchedulePagerItem = new SchedulePagerItem(mContext);
		List<clsScheduleInfo> scheduleList = mDateSchedulList.get(position);
		oSchedulePagerItem.setData(scheduleList, btnAddClickListener, mItemClickListener);
		((ViewPager) container).addView(oSchedulePagerItem, 0);
		return oSchedulePagerItem;
	}

}
