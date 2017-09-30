package com.adsale.HEATEC.adapter;

import java.util.List;

import com.adsale.HEATEC.database.model.clsMapFloor;
import com.adsale.HEATEC.view.MapFloorView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MapFloorAdapter extends BaseAdapter   {
	public static String TAG = "MapFloorAdapter";

	private Context mContext;
 
	private List<clsMapFloor> mocolMapFloors;
 

	public MapFloorAdapter(Context context,  List<clsMapFloor> pcolMapFloors) {
		super();
		mContext = context;
		 
		mocolMapFloors = pcolMapFloors;
	}

	@Override
	public int getCount() {
		if (null != mocolMapFloors)
			return mocolMapFloors.size();
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setListData(List<clsMapFloor> pocolMapFloors ) {
		this.mocolMapFloors = pocolMapFloors;
		 
	}

	@Override
	public clsMapFloor getItem(int position) {
		return mocolMapFloors.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MapFloorView itemView;
		
		clsMapFloor oItem = mocolMapFloors.get(position);
		if (convertView != null) {
			MapFloorView oEventItemView = (MapFloorView) convertView;
			clsMapFloor oClsMapFloor = oEventItemView.getData();
			if (!oClsMapFloor.getMapFloorID() .equals(oItem.getMapFloorID())) {
				oEventItemView.destroyView();
				itemView = new MapFloorView(mContext);
				itemView.setData(oItem);
			} else {
				oEventItemView.reloadView();
				itemView = oEventItemView;
			}
		} else {
			itemView = new MapFloorView(mContext);
			itemView.setData(oItem);
		}
		return itemView;

	}

	 

}