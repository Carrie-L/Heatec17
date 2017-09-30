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
import com.adsale.HEATEC.activity.ExhibitorListActivity;
import com.adsale.HEATEC.adapter.CategoryAdapter;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.CountryDBHelper;
import com.adsale.HEATEC.database.IndustryDBHelper;
import com.adsale.HEATEC.database.model.ICategory;
import com.adsale.HEATEC.database.model.clsSection;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.SideBar;

public class CategoryListFragment extends BaseFragment {
	public static String TAG = "CategoryListFragment";

	private SideBar indexBar;
	private ListView lstCategory;
	private TextView txtNoData;
	private TextView mDialogText;
	private int mLanguage = 0;
	private CategoryAdapter mCategoryAdapter;
	private List<ICategory> mcolCategory;
	private List<clsSection> IndexArray;
	private int mCategoryType = -1; // 1-industry 2-country
	private Context mContext;
	private View mBaseView;
	private String mTitle = "";

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_catergorylist, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mLanguage = SystemMethod.getCurLanguage(mContext);

		Intent intent = ((Activity) mContext).getIntent();

		if (savedInstanceState != null) {
			mCategoryType = savedInstanceState.getInt("CategoryType");
		} else {
			mCategoryType = intent.getIntExtra("CategoryType", -1);
		}

		setupView();
	}

	private void findView() {
		lstCategory = (ListView) mBaseView.findViewById(R.id.lstCategory);
		txtNoData = (TextView) mBaseView.findViewById(R.id.txtNoData);
		indexBar = (SideBar) mBaseView.findViewById(R.id.sideBar);
		mDialogText = (TextView) mBaseView.findViewById(R.id.DialogText);
	}

	public void setupView() {
		mDialogText.setVisibility(View.INVISIBLE);

		indexBar.setTextView(mDialogText);
		if (mCategoryType == 1) {
			IndustryDBHelper oIndustryDBHelper = new IndustryDBHelper(mContext);
			mcolCategory = oIndustryDBHelper.getIndustryList(mLanguage);
			IndexArray = oIndustryDBHelper.getIndexArray(mLanguage);
		} else {
			CountryDBHelper oCountryDBHelper = new CountryDBHelper(mContext);
			mcolCategory = oCountryDBHelper.getCountryList(mLanguage);
			IndexArray = oCountryDBHelper.getIndexArray(mLanguage);
		}

		IndexArray = SystemMethod.ChangeIndexSEQ(IndexArray);
		if (mcolCategory != null && mcolCategory.size() > 0) {
			List<ICategory> oTempList = new ArrayList<ICategory>();
			for (ICategory oclsCategory : mcolCategory) {
				if (oclsCategory.getSort(mLanguage).equals("#")) {
					oTempList.add(oclsCategory);
				} else {
					break;
				}
			}
			if (oTempList.size() > 0) {
				mcolCategory.removeAll(oTempList);
				mcolCategory.addAll(mcolCategory.size(), oTempList);
			}

		}

		mCategoryAdapter = new CategoryAdapter(mContext, mLanguage, mcolCategory);
		mCategoryAdapter.setSectionList(IndexArray);
		indexBar.setListView(lstCategory, mCategoryAdapter, IndexArray);

		lstCategory.setAdapter(mCategoryAdapter);
		lstCategory.setEmptyView(txtNoData);
		lstCategory.setOnItemClickListener(mItemClickListener);

	}

	public CategoryAdapter getAdapter() {
		return mCategoryAdapter;
	}

	private OnItemClickListener mItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ICategory oclsCategory = mCategoryAdapter.getItem(position);
			if (oclsCategory != null) {
				Intent intent = new Intent(mContext, ExhibitorListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				String strCatergoryID = oclsCategory.getCategoryID();
				if (mCategoryType == 1) {
					intent.putExtra("Industry", strCatergoryID);
				} else {
					intent.putExtra("Country", strCatergoryID);
				}
				intent.putExtra("Title", oclsCategory.getCategoryName(SystemMethod.getCurLanguage(mContext)));
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		}
	};

}
