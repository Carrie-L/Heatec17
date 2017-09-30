package com.adsale.HEATEC.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.SideBar;
import com.baidu.mobstat.StatService;

public class MyExhibitorListFragment extends BaseFragment {
	public static final String TAG = "MyExhibitorListFragment";
	public static final String EVENTID = "MyExListFgItem";

	private Context mContext;
	private View mBaseView;

	private SideBar indexBar;

	private ListView lstExhibitor;
	private TextView txtNoData;
	private TextView mDialogText;

	private int mLanguage = 0;

	private ExhibitorAdapter mExhibitorAdapter;
	private List<clsExhibitor> mcolExhibitors;
	private List<clsSection> IndexArray;

	private boolean mEnableBack;
	private String mTitle;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_my_exhibitor, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mContext = getActivity();

		setupView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		outState.putBoolean("EnableBack", mEnableBack);
	}

	private void findView() {
		lstExhibitor = (ListView) mBaseView.findViewById(R.id.lstExhibitor);
		txtNoData = (TextView) mBaseView.findViewById(R.id.txtNoData);
		indexBar = (SideBar) mBaseView.findViewById(R.id.sideBar);
		mDialogText = (TextView) mBaseView.findViewById(R.id.DialogText);
	}

	private void setupView() {
		App.SearchIndustryCount = 0;

		mLanguage = SystemMethod.getCurLanguage(mContext);

		mDialogText.setVisibility(View.INVISIBLE);
		indexBar.setTextView(mDialogText);
		lstExhibitor.setEmptyView(txtNoData);
		lstExhibitor.setOnItemClickListener(mItemClickListener);

		bindData();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1001) {
			bindData();
		}
	};

	public void bindData() {
		ExhibitorDBHelper oExhibitorDBHelper = new ExhibitorDBHelper(mContext);
		mcolExhibitors = oExhibitorDBHelper.SearchExhibitorList(mLanguage, "", "", "", true);
		IndexArray = oExhibitorDBHelper.getIndexArray(mLanguage, "", "", "", true);
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
		if (mExhibitorAdapter == null) {
			mExhibitorAdapter = new ExhibitorAdapter(mContext, mLanguage, mcolExhibitors);
			mExhibitorAdapter.setSectionList(IndexArray);
			lstExhibitor.setAdapter(mExhibitorAdapter);
		} else {
			mExhibitorAdapter.setListData(mcolExhibitors, mLanguage);
			mExhibitorAdapter.setSectionList(IndexArray);
			mExhibitorAdapter.notifyDataSetChanged();
		}
		indexBar.setListView(lstExhibitor, mExhibitorAdapter, IndexArray);

	}

	private OnItemClickListener mItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			clsExhibitor oItem = mExhibitorAdapter.getItem(position);

			StatService.onEvent(mContext, EVENTID, "pass", 1);

			if (oItem != null) {
				Intent intent = new Intent(mContext, ExhibitorDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("Exhibitor", oItem);
				startActivityForResult(intent, 1001);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		}
	};

}
