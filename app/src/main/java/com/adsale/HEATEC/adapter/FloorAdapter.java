package com.adsale.HEATEC.adapter;

import java.util.List;

import com.adsale.HEATEC.database.model.ICategory;
import com.adsale.HEATEC.view.FloorView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FloorAdapter extends BaseAdapter {
	public static String TAG = "FloorAdapter";

	private Context mContext;
	private int curLanguage;
	private List<ICategory> mocolCategorys;

	public FloorAdapter(Context context, int pLanguage, List<ICategory> pcolCategorys) {
		super();
		mContext = context;
		curLanguage = pLanguage;
		mocolCategorys = pcolCategorys;
	}

	@Override
	public int getCount() {
		if (null != mocolCategorys)
			return mocolCategorys.size();
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setListData(List<ICategory> pocolCategorys, int pLanguage) {
		this.mocolCategorys = pocolCategorys;
		curLanguage = pLanguage;
	}

	@Override
	public ICategory getItem(int position) {
		return mocolCategorys.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ICategory oclsCategory = mocolCategorys.get(position);

		FloorView holder = null;
		if (convertView != null) {
			FloorView oFloorView = (FloorView) convertView;
			oFloorView.setData(oclsCategory, curLanguage);
			holder = oFloorView;
		} else {
			holder = new FloorView(mContext);
			holder.setData(oclsCategory, curLanguage);
		}

		return holder;

	}

}