package com.adsale.HEATEC.base;

import java.util.ArrayList;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T,V> extends BaseAdapter {
	public ArrayList<T> mLists;
	public Context mContext;
	public V view;

	public MyBaseAdapter(ArrayList<T> lists, Context context) {
		super();
		this.mLists = lists;
		this.mContext = context;
	}

	public MyBaseAdapter(ArrayList<T> mLists, Context mContext, V view) {
		super();
		this.mLists = mLists;
		this.mContext = mContext;
		this.view = view;
	}

	@Override
	public int getCount() {
		return mLists.size();
	}

	@Override
	public Object getItem(int position) {
		return mLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	

}
