package com.adsale.HEATEC.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.ExhibitorDBHelper;
import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsScheduleInfo;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.view.ExhibitorDtlView;
import com.adsale.HEATEC.view.NoteView;
import com.adsale.HEATEC.view.ScheduleInfoView;

public class ExhibitorDetailFragment extends BaseFragment {
	public static final String TAG = "ExhibitorDetailFragment";

	private Context mContext;
	private View mBaseView;

	private TextView txtCompanyName;
	private RelativeLayout lyAddCollection;
	private ImageView imgAddCollection;
	private TextView txtAddCollection;
	private Button btnTab1, btnTab2, btnTab3;

	private clsExhibitor mclsExhibitor;
	private int mCurLanguage;
	private ExhibitorDtlView mExhibitorDtlView;
	private NoteView mNoteView;

	private String mTitle;
	private ScheduleInfoView mScheduleInfoView;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_exhibitor_detal, null);
		mContext = getActivity();
		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mclsExhibitor = savedInstanceState.getParcelable("Exhibitor");
		} else {
			mclsExhibitor = ((Activity) mContext).getIntent().getParcelableExtra("Exhibitor");
		}
		setupView();
	}

	private void findView() {
		txtCompanyName = (TextView) mBaseView.findViewById(R.id.txtCompanyName);
		lyAddCollection = (RelativeLayout) mBaseView.findViewById(R.id.lyAddCollection);
		imgAddCollection = (ImageView) mBaseView.findViewById(R.id.imgAddCollection);
		txtAddCollection = (TextView) mBaseView.findViewById(R.id.txtAddCollection);

		btnTab1 = (Button) mBaseView.findViewById(R.id.btnTab1);
		btnTab2 = (Button) mBaseView.findViewById(R.id.btnTab2);
		btnTab3 = (Button) mBaseView.findViewById(R.id.btnTab3);

		mExhibitorDtlView = (ExhibitorDtlView) mBaseView.findViewById(R.id.exhibitorDtlView1);
		mScheduleInfoView = (ScheduleInfoView) mBaseView.findViewById(R.id.scheduleInfoView1);
		mNoteView = (NoteView) mBaseView.findViewById(R.id.noteView1);
	}

	private void setupView() {
		mCurLanguage = SystemMethod.getCurLanguage(mContext);

		txtCompanyName.setText(mclsExhibitor.getCompanyName(mCurLanguage));
		lyAddCollection.setOnClickListener(lyAddCollectionClick);

		btnTab1.setOnClickListener(mTabClickListener);
		btnTab2.setOnClickListener(mTabClickListener);
		btnTab3.setOnClickListener(mTabClickListener);

		mclsExhibitor = new ExhibitorDBHelper(mContext).getExhibitor(mclsExhibitor.getCompanyID());
		if (mclsExhibitor.getIsFavourite() == 0) {
			imgAddCollection.setImageResource(R.drawable.collect01);
			txtAddCollection.setText(R.string.txtAddCollection);
		} else {
			imgAddCollection.setImageResource(R.drawable.collect02);
			txtAddCollection.setText(R.string.txtAddCollection2);
		}

		mScheduleInfoView.setBtnAddClickListener(btnAddClickListener);
		mScheduleInfoView.setListItemClickListener(mScheduleItemClickListener);

		mExhibitorDtlView.setFragment(ExhibitorDetailFragment.this);
		mExhibitorDtlView.setData(mclsExhibitor);

		mScheduleInfoView.setData(mclsExhibitor);
		mNoteView.setData(mclsExhibitor);

		btnTab1.performClick();
	}

	OnClickListener lyAddCollectionClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			ExhibitorDBHelper oHelper = new ExhibitorDBHelper(mContext);

			if (mclsExhibitor.getIsFavourite() == 0) {
				mclsExhibitor.setIsFavourite(1);
				if (oHelper.Update(mclsExhibitor)) {
					txtAddCollection.setText(R.string.txtAddCollection2);
					imgAddCollection.setImageResource(R.drawable.collect02);
				}
			} else {
				mclsExhibitor.setIsFavourite(0);
				if (oHelper.Update(mclsExhibitor)) {
					txtAddCollection.setText(R.string.txtAddCollection);
					imgAddCollection.setImageResource(R.drawable.collect01);
				}
			}

			FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
			MyExhibitorListFragment oFragment = (MyExhibitorListFragment) fm.findFragmentByTag(MyExhibitorListFragment.TAG);
			if (oFragment != null) {
				oFragment.bindData();
			}
		}
	};

	public void refCollection() {
		// 添加到我的参展商
		txtAddCollection.setText(R.string.txtAddCollection2);
		imgAddCollection.setImageResource(R.drawable.collect02);

		FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
		MyExhibitorListFragment oFragment = (MyExhibitorListFragment) fm.findFragmentByTag(MyExhibitorListFragment.TAG);
		if (oFragment != null) {
			oFragment.bindData();
		}
	}

	private OnClickListener mTabClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int intID = v.getId();
			if (intID == R.id.btnTab1) {
				// SystemMethod.trackLogJson(mContext, 100, "page",
				// "mExhibitorDtlView", null);
				SystemMethod.trackViewLog(mContext, 101, "Page", "mExhibitorDtlView", null, null);
				btnTab1.setEnabled(false);
				btnTab2.setEnabled(true);
				btnTab3.setEnabled(true);

				mExhibitorDtlView.setVisibility(View.VISIBLE);
				mScheduleInfoView.setVisibility(View.GONE);
				mNoteView.setVisibility(View.GONE);

			} else if (intID == R.id.btnTab2) {
				SystemMethod.trackViewLog(mContext, 102, "Page", "mScheduleInfoView", null, null);
				// SystemMethod.trackLogJson(mContext, 100, "page",
				// "mScheduleInfoView", null);

				btnTab1.setEnabled(true);
				btnTab2.setEnabled(false);
				btnTab3.setEnabled(true);

				mExhibitorDtlView.setVisibility(View.GONE);
				mScheduleInfoView.setVisibility(View.VISIBLE);
				mNoteView.setVisibility(View.GONE);

			} else if (intID == R.id.btnTab3) {
				// SystemMethod.trackLogJson(mContext, 100, "page", "mNoteView",
				// null);
				SystemMethod.trackViewLog(mContext, 103, "Page", "mNoteView", null, null);
				btnTab1.setEnabled(true);
				btnTab2.setEnabled(true);
				btnTab3.setEnabled(false);

				mExhibitorDtlView.setVisibility(View.GONE);
				mScheduleInfoView.setVisibility(View.GONE);
				mNoteView.setVisibility(View.VISIBLE);

			}
		}
	};

	public void ResetScheduleData() {
		if (mScheduleInfoView != null) {
			mScheduleInfoView.ResetData();
		}
	}

	OnItemClickListener mScheduleItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			clsScheduleInfo oClsScheduleInfo = (clsScheduleInfo) parent.getAdapter().getItem(position);

			if (oClsScheduleInfo != null) {
				if (!App.isTablet) {
//					Intent intent = new Intent(mContext, ScheduleEditActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					intent.putExtra("ExhibitorName", mclsExhibitor.getCompanyName(mCurLanguage));
//					intent.putExtra("ScheduleInfo", oClsScheduleInfo);
//					startActivityForResult(intent, ScheduleFragment.REQUEST_CODE_Edit);
//					if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//						((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//					}
				}
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == ScheduleFragment.REQUEST_CODE_Edit) {
//			ResetScheduleData();
//		}
	};

	OnClickListener btnAddClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(mContext, ScheduleEditActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.putExtra("CompanyID", mclsExhibitor.getCompanyID());
//			intent.putExtra("ExhibitorName", mclsExhibitor.getCompanyName(mCurLanguage));
//
//			LogUtil.i(TAG, "name="+ mclsExhibitor.getCompanyName(mCurLanguage)+",CompanyID="+mclsExhibitor.getCompanyID());
//			intent.putExtra("Title", getString(R.string.title_agenda));
//			startActivityForResult(intent, ScheduleFragment.REQUEST_CODE_Edit);
//			if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//			}

		}
	};

}
