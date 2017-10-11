package com.adsale.HEATEC.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.util.SystemMethod;

import sanvio.libs.adapter.BasicAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScheduleInfoAdapter extends BasicAdapter {
	private List<ScheduleInfo> mDataList;

	private Context mContext;
	private LayoutInflater inflater;
	private int curLanguage;

	private class Holder {
		TextView txtLable;
		TextView txtTitle;
		TextView txtStartTime;
	}

	public ScheduleInfoAdapter(Context c, List<ScheduleInfo> list) {
		super();
		mContext = c;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		curLanguage = SystemMethod.getCurLanguage(mContext);
		if (list.isEmpty()) {
			mDataList = new ArrayList<ScheduleInfo>();
		} else {
			mDataList = list;
		}
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public ScheduleInfo getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder temp;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.view_scheduleinfo_item, null);
			temp = new Holder();
			temp.txtLable = (TextView) convertView.findViewById(R.id.txtLable);
			temp.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			temp.txtStartTime = (TextView) convertView.findViewById(R.id.txtStartTime);
			convertView.setTag(temp);
		} else {
			temp = (Holder) convertView.getTag();
		}

		ScheduleInfo oclScheduleInfo = mDataList.get(position);

//		temp.txtLable.setText(String.format(mContext.getString(R.string.ScheduleInfo_lable), position + 1));
//		temp.txtTitle.setText(oclScheduleInfo.getTitle());
//		temp.txtStartTime.setText(String.format(mContext.getString(R.string.ScheduleInfo_startTime), oclScheduleInfo.getStartTime(curLanguage)));

		return convertView;
	}

}