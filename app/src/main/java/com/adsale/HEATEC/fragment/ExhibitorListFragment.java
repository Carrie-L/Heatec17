package com.adsale.HEATEC.fragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Filter.FilterListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.activity.ExhibitorDetailActivity;
import com.adsale.HEATEC.adapter.ExhibitorAdapter;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsSection;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.SideBar;

public class ExhibitorListFragment extends BaseFragment {
	public static final String TAG = "ExhibitorListFragment";
	private SideBar indexBar;
	private ListView lstExhibitor;
	private TextView txtNoData;
	private TextView mDialogText;
	private EditText editFilter;
	private int mLanguage = 0;
	private ExhibitorAdapter mExhibitorAdapter;
	private String mCountryID, mIndustry, mFloorID;
	private List<clsExhibitor> mcolExhibitors;
	private List<clsSection> IndexArray;
	private Context mContext;
	private View mBaseView;
	private boolean mAddSchedule = false;

	private String mTitle;
	private int currentItem;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.activity_exhibitors, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mLanguage = SystemMethod.getCurLanguage(mContext);

		Intent intent = ((Activity) mContext).getIntent();

		if (savedInstanceState != null) {
			mAddSchedule = savedInstanceState.getBoolean("AddSchedule", false);
			mCountryID = savedInstanceState.getString("Country");
			mIndustry = savedInstanceState.getString("Industry");
			mFloorID = savedInstanceState.getString("Floor");
			currentItem = savedInstanceState.getParcelable("currentItem");
		} else {
			mAddSchedule = intent.getBooleanExtra("AddSchedule", false);
			mCountryID = intent.getStringExtra("Country");
			mIndustry = intent.getStringExtra("Industry");
			mFloorID = intent.getStringExtra("Floor");
			currentItem = intent.getIntExtra("currentItem", 0);
		}

		setupView();

	}

	private void findView() {
		lstExhibitor = (ListView) mBaseView.findViewById(R.id.lstExhibitor);

		txtNoData = (TextView) mBaseView.findViewById(R.id.txtNoData);
		indexBar = (SideBar) mBaseView.findViewById(R.id.sideBar);

		editFilter = (EditText) mBaseView.findViewById(R.id.editFilter);
		mDialogText = (TextView) mBaseView.findViewById(R.id.DialogText);
	}

	public void setupView() {
		if (!TextUtils.isEmpty(mIndustry)) {
			App.SearchIndustryCount++;
		}

		mDialogText.setVisibility(View.INVISIBLE);
		indexBar.setTextView(mDialogText);

		ExhibitorDBHelper oExhibitorDBHelper = new ExhibitorDBHelper(mContext);

		mcolExhibitors = oExhibitorDBHelper.SearchExhibitorList(mLanguage, mIndustry, mCountryID, mFloorID, false);

		IndexArray = oExhibitorDBHelper.getIndexArray(mLanguage, mIndustry, mCountryID, mFloorID, false);
		IndexArray = SystemMethod.ChangeIndexSEQ(IndexArray);

		if (mcolExhibitors != null && mcolExhibitors.size() > 0) {
			List<clsExhibitor> oTempList = new ArrayList<clsExhibitor>();
			for (clsExhibitor oclsExhibitor : mcolExhibitors) {
				if (oclsExhibitor.getSort(mLanguage).equals("#")) {
					oTempList.add(oclsExhibitor);
				} else {
					break;
				}
			}
			if (oTempList.size() > 0) {
				mcolExhibitors.removeAll(oTempList);
				mcolExhibitors.addAll(mcolExhibitors.size(), oTempList);
			}

		}

		// 二次排序，根据Logo字段的数字
		//2016.11.15注释，因为logo字段为空
//		clsExhibitor exhibitor = new clsExhibitor();
//		exhibitor.setCurrentLang(mLanguage);
//		Collections.sort(mcolExhibitors, new OrderIndex());

//		mExhibitorAdapter = new ExhibitorAdapter(mContext, mLanguage, mcolExhibitors);
//		mExhibitorAdapter.setSectionList(IndexArray);
//		indexBar.setListView(lstExhibitor, mExhibitorAdapter, IndexArray);
//
//		lstExhibitor.setAdapter(mExhibitorAdapter);
//		lstExhibitor.setEmptyView(txtNoData);
//		lstExhibitor.setOnItemClickListener(mItemClickListener);
//
//		editFilter.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				mExhibitorAdapter.getFilter().filter(s.toString(), new myFilterListener());
//
//			}
//		});
	}

	// 升序
	public class OrderIndex implements Comparator<clsExhibitor> {

		private static final String TAG = "OrderIndex";
		private String lhs_index, rhs_index;

		@Override
		public int compare(clsExhibitor lhs, clsExhibitor rhs) {
			lhs_index = completeIndex(orderColumn(lhs));
			rhs_index = completeIndex(orderColumn(rhs));

			if (lhs.getSort(mLanguage).equals(rhs.getSort(mLanguage))) {// 当sort相同时才比较

				if (lhs_index.compareTo(rhs_index) > 0) {
					return 1;
				} else if (lhs_index.compareTo(rhs_index) < 0) {
					return -1;
				} else
					return 0;
			}
			return 0;

		}

		/**
		 * 如果index<10,就补全数字，如03、05
		 * 
		 * @param index
		 *            截取到的数字序号
		 * @return
		 */
		private String completeIndex(String index) {
			if (index.length() == 1) {
				LogUtil.i(TAG, "index=" + index);
				index = "0" + index;
			}
			return index;
		}

	}

	private String orderColumn(clsExhibitor exhibitor) {// logo: en-tc-sc
		if (mLanguage == 0) {
			return exhibitor.getLogo().split("-")[1];
		} else if (mLanguage == 1) {
			return exhibitor.getLogo().split("-")[0];
		} else {
			return exhibitor.getLogo().split("-")[2];
		}
	}

	private class myFilterListener implements FilterListener {

		@Override
		public void onFilterComplete(int count) {

//			if (editFilter.getText().toString().trim().isEmpty()) {
//				indexBar.setListView(lstExhibitor, mExhibitorAdapter, IndexArray);
//			} else {
//				List<clsSection> ocolSections = getSectionList(mExhibitorAdapter.getData());
//				indexBar.setIndexArray(ocolSections);
//			}
		}

	}

	private List<clsSection> getSectionList(List<clsExhibitor> pocolExhibitors) {
		List<clsSection> ocolSections = new ArrayList<clsSection>();
		clsSection oclsSection, oclsLastReacord = null;
		for (clsExhibitor oclsExhibitor : pocolExhibitors) {
			if (oclsLastReacord == null) {
				oclsSection = new clsSection(oclsExhibitor.getSort(mLanguage), ocolSections.size());
				ocolSections.add(oclsSection);
				oclsLastReacord = oclsSection;
			} else {
				if (!oclsLastReacord.getLable().equals(oclsExhibitor.getSort(mLanguage))) {
					oclsSection = new clsSection(oclsExhibitor.getSort(mLanguage), ocolSections.size());
					ocolSections.add(oclsSection);
					oclsLastReacord = oclsSection;
				}
			}
		}
		return ocolSections;

	}

//	private OnItemClickListener mItemClickListener = new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////			clsExhibitor oItem = mExhibitorAdapter.getItem(position);
//
//			// SystemMethod.trackLogJson(mContext, 100, "page",
//			// oItem.getCompanyNameEN(), null);
//
//			if (oItem != null) {
//				if (mAddSchedule) {
//
//					LogUtil.i(TAG, "mAddSchedule: setResult(Activity.RESULT_OK");
//
////					Intent intent = new Intent(mContext, ScheduleEditActivity.class);
////					intent.putExtra("CompanyID", oItem.getCompanyID());
////					intent.putExtra("ExhibitorName", oItem.getCompanyName(SystemMethod.getCurLanguage(mContext)));
////					intent.putExtra("currentItem", currentItem);
////					startActivity(intent);
////					Activity oActivity = getActivity();
////					oActivity.setResult(Activity.RESULT_OK, intent);
////					oActivity.finish();
//				} else {
//
//					LogUtil.i(TAG, "Intent: ExhibitorDetailActivity");
//
//					Intent intent = new Intent(mContext, ExhibitorDetailActivity.class);
//					intent.putExtra("Exhibitor", oItem);
//					intent.putExtra("currentItem", currentItem);
//					startActivity(intent);
//				}
//				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//				}
//
//			}
//		}
//	};

}
