package com.adsale.HEATEC.adapter;

import com.adsale.HEATEC.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavListADT extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;

	private Integer[] mIconList;
	private int[] mHideList;
	private String[] mNavList;
	
	private int temp=0;

	private class NavHolder {
		ImageView imgNavIcon;
		TextView textNvaName;
	}

	public NavListADT(Context c, Integer[] iconList, String[] navList,int[] hideList) {
		mContext = c;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mIconList = iconList;
		mNavList = navList;
		mHideList=hideList;
		
		if(mHideList!=null){
			for (int i = 0; i < mHideList.length; i++) {
				if(mHideList[i]!=0){
					temp++;
				}
			}
		}
		
	}

	@Override
	public int getCount() {

		if(mHideList==null){
			return mIconList == null ? 0 : mIconList.length;
		}else{
			return temp;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NavHolder temp;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.view_exhibitor_nav_item, null);
			temp = new NavHolder();
			temp.imgNavIcon = (ImageView) convertView.findViewById(R.id.imgNavIcon);
			temp.textNvaName = (TextView) convertView.findViewById(R.id.textNvaName);
			convertView.setTag(temp);
		} else {
			temp = (NavHolder) convertView.getTag();
		}

		temp.imgNavIcon.setImageResource(mIconList[position]);
		temp.textNvaName.setText(mNavList[position]);

		return convertView;
	}

}
