package com.adsale.HEATEC.view;

import java.util.List;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.adapter.ScheduleInfoAdapter;
import com.adsale.HEATEC.database.ScheduleInfoDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsScheduleInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScheduleInfoView extends RelativeLayout {
	public static final String TAG = "ScheduleInfoView";
	public static final int REQUEST_CODE_Edit = 1001;

	private Context mContext;

	private Button btnAdd;
	private TextView txtNoData;
	private ListView listView;

	private clsExhibitor mClsExhibitor;
	private ScheduleInfoAdapter mAdapter;

	private OnClickListener btnAddClickListener;

	public void setBtnAddClickListener(OnClickListener listener) {
		btnAddClickListener = listener;
	}

	private OnItemClickListener mItemClickListener;

	public void setListItemClickListener(OnItemClickListener listener) {
		mItemClickListener = listener;
	}

	public ScheduleInfoView(Context context) {
		super(context);
		setupView();
	}

	public ScheduleInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}

	public ScheduleInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupView();
	}

	private void setupView() {
		mContext = getContext();
		LayoutInflater.from(mContext).inflate(R.layout.view_scheduleinfos, this);
		findView();
	}

	private void findView() {
		btnAdd = (Button) findViewById(R.id.btnAdd);
		txtNoData = (TextView) findViewById(R.id.txtNoData);
		listView = (ListView) findViewById(R.id.listView);
	}

	public void setData(clsExhibitor exhibitor) {
		mClsExhibitor = exhibitor;
		listView.setEmptyView(txtNoData);
		List<clsScheduleInfo> oColScheduleInfos = new ScheduleInfoDBHelper(mContext).getScheduleInfoList(mClsExhibitor.getCompanyID());
		mAdapter = new ScheduleInfoAdapter(mContext, oColScheduleInfos);
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(mItemClickListener);
		btnAdd.setOnClickListener(btnAddClickListener);
	}

	public void ResetData() {
		List<clsScheduleInfo> oColScheduleInfos = new ScheduleInfoDBHelper(mContext).getScheduleInfoList(mClsExhibitor.getCompanyID());
		mAdapter = new ScheduleInfoAdapter(mContext, oColScheduleInfos);
		listView.setAdapter(mAdapter);
	}
}
