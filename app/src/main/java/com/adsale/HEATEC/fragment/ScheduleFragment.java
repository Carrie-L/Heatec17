//package com.adsale.HEATEC.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//
//import com.adsale.HEATEC.R;
//import com.adsale.HEATEC.activity.ExhibitorListActivity;
//import com.adsale.HEATEC.adapter.SchedulePagerADT;
//import com.adsale.HEATEC.base.BaseFragment;
//import com.adsale.HEATEC.database.model.clsScheduleInfo;
//import com.adsale.HEATEC.util.SystemMethod;
//
//public class ScheduleFragment extends BaseFragment {
//	public static final String TAG = "ScheduleFragment";
//	public static final int REQUEST_CODE_Edit = 1001;
//	public static final int REQUEST_CODE_SELEXHIBITOR = 1002;
//
//	private Context mContext;
//	private View mBaseView;
//
//	private Button btnDate1, btnDate2, btnDate3;
//	private ViewPager schedulePager;
//	private SchedulePagerADT mSchedulePagerADT;
//	private String mTitle;
//	private int currentItem=0;
//
//	@Override
//	public View initView(LayoutInflater inflater) {
//		mBaseView = inflater.inflate(R.layout.activity_schedule,null);
//		mContext = getActivity();
//
//		findView();
//
//		return mBaseView;
//	}
//
//	@Override
//	public void initData(Bundle savedInstanceState) {
//
//		setupView();
//	}
//
//	private void findView() {
//		btnDate1 = (Button) mBaseView.findViewById(R.id.btnDate01);
//		btnDate2 = (Button) mBaseView.findViewById(R.id.btnDate02);
//		btnDate3 = (Button) mBaseView.findViewById(R.id.btnDate03);
//
//		schedulePager = (ViewPager) mBaseView.findViewById(R.id.schedulePager);
//	}
//
//	private void setupView() {
//		btnDate1.setOnClickListener(dateClickListener);
//		btnDate2.setOnClickListener(dateClickListener);
//		btnDate3.setOnClickListener(dateClickListener);
//
//		mSchedulePagerADT = new SchedulePagerADT(mContext, btnAddClickListener, mItemClickListener);
//		schedulePager.setAdapter(mSchedulePagerADT);
//		schedulePager.setOnPageChangeListener(pageChangeListener);
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (resultCode == Activity.RESULT_OK) {
//			if (requestCode == REQUEST_CODE_Edit) {
//				ResetData();
//			}
//		}
//	}
//
//	public void ResetData() {
//		int currentItem = schedulePager.getCurrentItem();
//
//		mSchedulePagerADT = new SchedulePagerADT(mContext, btnAddClickListener, mItemClickListener);
//		schedulePager.setAdapter(mSchedulePagerADT);
//		schedulePager.setCurrentItem(currentItem);
//	}
//
//	OnClickListener dateClickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			int id = v.getId();
//			if (id == R.id.btnDate01) {
//				btnDate1.setEnabled(false);
//				btnDate2.setEnabled(true);
//				btnDate3.setEnabled(true);
//				schedulePager.setCurrentItem(0, true);
//				currentItem=0;
//			} else if (id == R.id.btnDate02) {
//				btnDate1.setEnabled(true);
//				btnDate2.setEnabled(false);
//				btnDate3.setEnabled(true);
//				schedulePager.setCurrentItem(1, true);
//				currentItem=1;
//			} else if (id == R.id.btnDate03) {
//				btnDate1.setEnabled(true);
//				btnDate2.setEnabled(true);
//				btnDate3.setEnabled(false);
//				schedulePager.setCurrentItem(2, true);
//				currentItem=2;
//			}
//		}
//	};
//
//	OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
//		@Override
//		public void onPageSelected(int position) {
//			if (position == 0) {
//				btnDate1.setEnabled(false);
//				btnDate2.setEnabled(true);
//				btnDate3.setEnabled(true);
//				currentItem=0;
//			} else if (position == 1) {
//				btnDate1.setEnabled(true);
//				btnDate2.setEnabled(false);
//				btnDate3.setEnabled(true);
//				currentItem=1;
//			} else if (position == 2) {
//				btnDate1.setEnabled(true);
//				btnDate2.setEnabled(true);
//				btnDate3.setEnabled(false);
//				currentItem=2;
//			}
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int position) {
//
//		}
//	};
//
//	OnClickListener btnAddClickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//				Intent intent = new Intent(mContext, ExhibitorListActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.putExtra("AddSchedule", true);
//				intent.putExtra("currentItem",currentItem );
//			//	getActivity().startActivityForResult(intent, ScheduleFragment.REQUEST_CODE_SELEXHIBITOR);
//				startActivity(intent);
//				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//				}
//
//		}
//	};
//
//	OnItemClickListener mItemClickListener = new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			clsScheduleInfo oClsScheduleInfo = (clsScheduleInfo) parent.getAdapter().getItem(position);
//			if (oClsScheduleInfo != null) {
//					Intent intent = new Intent(mContext, ScheduleEditActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					intent.putExtra("ScheduleInfo", oClsScheduleInfo);
//					intent.putExtra("currentItem",currentItem );
//					getActivity().startActivityForResult(intent, ScheduleFragment.REQUEST_CODE_Edit);
//					if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//						((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//					}
//			}
//		}
//	};
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		ResetData();
//	}
//
//
//}
