package com.adsale.HEATEC.view;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.clsMapFloor;
import com.adsale.HEATEC.util.SystemMethod;

import sanvio.libs.util.DisplayUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MapFloorView extends RelativeLayout {
	private Context mContext;
	private clsMapFloor mclsMapFloor;

	private TextView textMapFloor;

	public MapFloorView(Context mContext) {
		super(mContext);
		this.mContext = mContext;
		createViews();
	}

	private void createViews() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.item_map_floor, this);

		int padding = DisplayUtils.dip2px(mContext, 10);
		setPadding(padding, padding, padding, padding);

		textMapFloor = (TextView) findViewById(R.id.textMapFloor);
	}

	public void setData(clsMapFloor pclsMapFloor) {
		mclsMapFloor = pclsMapFloor;
		reloadView();
	}

	public void reloadView() {
		textMapFloor.setText(mclsMapFloor.getName(SystemMethod.getCurLanguage(mContext)));

	}

	public void destroyView() {

	}

	public clsMapFloor getData() {
		return mclsMapFloor;

	}

}
