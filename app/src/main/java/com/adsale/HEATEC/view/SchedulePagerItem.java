package com.adsale.HEATEC.view;

import java.util.List;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.ScheduleInfoAdapter;
import com.adsale.HEATEC.dao.ScheduleInfo;

import sanvio.libs.util.DisplayUtils;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SchedulePagerItem extends LinearLayout {
	private Context mContext;

	// private RelativeLayout mainLayout;
	private Button btnAdd;
	private TextView txtNoData;
	private ListView listView;

	private ScheduleInfoAdapter mAdapter;

	public SchedulePagerItem(Context context) {
		super(context);
		setupView();
	}

	public SchedulePagerItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public SchedulePagerItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.f_scheduleinfos, this);

		// mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

		btnAdd = (Button) findViewById(R.id.btnAdd);
		txtNoData = (TextView) findViewById(R.id.txtNoData);
		listView = (ListView) findViewById(R.id.listView);

		int padding = DisplayUtils.dip2px(mContext, 10);
		setPadding(padding, padding, padding, padding);
		listView.setBackgroundResource(R.drawable.border);
		listView.setDivider(new ColorDrawable(Color.BLACK));
		listView.setDividerHeight(DisplayUtils.dip2px(mContext, 1));
	}

	public void setData(List<ScheduleInfo> oColScheduleInfos, OnItemClickListener mItemClickListener) {
		listView.setEmptyView(txtNoData);
		mAdapter = new ScheduleInfoAdapter(mContext, oColScheduleInfos);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(mItemClickListener);
	}

	public void DestroyView() {

	}

}
