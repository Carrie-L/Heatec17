package com.adsale.HEATEC.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.activity.CategoryListActivity;
import com.adsale.HEATEC.activity.ExhibitorListActivity;
import com.adsale.HEATEC.activity.MyExhibitorListActivity;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.adapter.FtpExhibitorAdapter;
import com.adsale.HEATEC.adapter.NavListADT;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.model.ftpExDisplay;
import com.adsale.HEATEC.database.model.ftpInformation;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.baidu.mobstat.StatService;

public class ExhibitorNavFragment extends BaseFragment {
	public static final String TAG = "ExhibitorNavFragment";
	public static final String EVENTID = "ExNavF";

	private Context mContext;
	private View mBaseView;
	private ListView listView;
	private String mTitle;
	private Integer[] mFunctionPics;
	private String[] mFName = new String[5];
	private String[] mFunctionName;

	private String str_information;
	private ftpInformation information;
	private int mHide[] = new int[5];

	// ftp控制的自适应的列表
	private ListView lv_adaptive;
	private FtpExhibitorAdapter mFtpAdapter;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_exhibitor_nav, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mCurrLang = SystemMethod.getCurLanguage(mContext);

		setupView();
	}

	private void findView() {
		listView = (ListView) mBaseView.findViewById(R.id.listView1);
		lv_adaptive = (ListView) mBaseView.findViewById(R.id.lv_adaptive);
	}

	private void setupView() {
		App.SearchIndustryCount = 0;

		initData();

		listView.setAdapter(new NavListADT(mContext, mFunctionPics, mFunctionName, mHide));
		listView.setOnItemClickListener(itemClickListener);
	}

	private void initData() {
		mFunctionPics = new Integer[] { R.drawable.met_01, R.drawable.met_02, R.drawable.met_04, R.drawable.region, R.drawable.met_03 };
		information = SystemMethod.getInformation(mContext);
		LogUtil.d("TAG", "information---information：" + information);

		// 把数据放到数组里面
		for (int i = 0; i < information.getExhibitorList().getDisplay().hide.size(); i++) {
			mHide[i] = information.getExhibitorList().getDisplay().hide.get(i);
			switch (mCurrLang) {
			case 1:
				mFName[i] = information.getExhibitorList().getDisplay().en.get(i);
				break;
			case 2:
				mFName[i] = information.getExhibitorList().getDisplay().sc.get(i);
				break;
			default:
				mFName[i] = information.getExhibitorList().getDisplay().tc.get(i);
				break;
			}
		}

		// 把数组元素添加到list中
		List<String> listName = new ArrayList<String>();
		List<Integer> listPics = new ArrayList<Integer>();
		for (int i = 0; i < mFName.length; i++) {
			listName.add(mFName[i]);
			listPics.add(mFunctionPics[i]);
		}

		// 根据mHide中的值来删除List中的数据
		int y = 0;
		int x = 0;
		for (int i = 0; i < mHide.length; i++) {
			if (mHide[i] == 0) {
				// 由于每次删除一个元素后list会从新分布下标，
				// 所以为了在每次从新分布下标之后还能以正常的顺序删除元素，就将每次删除的个数减掉就可以了
				listName.remove(i - (y++));
				listPics.remove(i - (x++));
			}
		}
		
		// 吧List转换成数组
		mFunctionName = listName.toArray(new String[1]);
		mFunctionPics = (Integer[]) listPics.toArray(new Integer[1]);
		LogUtil.d("TAG", "mFunctionNamelength:" + mFunctionName.length);
		// //遍历数组
		// for (int j = 0; j < mFunctionName.length; j++) {
		// LogUtil.d("TAG","mFunctionName后:"+j +mFunctionName[j]);
		// LogUtil.d("TAG","mFunctionPics后:"+j +mFunctionPics[j]);
		// }

		if (information.contentList != null) {
			initFtpData();
		}

	}

	/**
	 * FtpExhibitorListAdapter
	 */
	private void initFtpData() {
		types = new ArrayList<Integer>();
		ArrayList<String> icon = new ArrayList<String>();
		fileUrls = new ArrayList<String>();
		itemNames = new ArrayList<String>();
		ftpExDisplay display = new ftpExDisplay();
		display = information.contentList.display;
		types = display.type;
		icon = display.icon;
		if (mCurrLang == 1) {
			itemNames = display.en;
			fileUrls = display.fileEN;
		} else if (mCurrLang == 2) {
			itemNames = display.sc;
			fileUrls = display.fileSC;
		} else {
			itemNames = display.tc;
			fileUrls = display.fileTC;
		}

		mFtpAdapter = new FtpExhibitorAdapter(mContext, icon, itemNames);

		lv_adaptive.setAdapter(mFtpAdapter);
		lv_adaptive.setOnItemClickListener(new OnFtpItemClickListener());
	}

	public class OnFtpItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = null;

			// 1- webcontent ; 2. webLink
			int type = types.get(position);
			String name1 = fileUrls.get(position).substring(fileUrls.get(position).lastIndexOf("/") + 1);// 001.zip
			String name2 = name1.substring(0, name1.length() - 4);// 001

			intent = new Intent(mContext, WebContentActivity.class);
			intent.putExtra("Title", itemNames.get(position));
			if (type == 1) {// Webcontent
				intent.putExtra("ftpExhiListName", name2);
				intent.putExtra("ftpExhiListDir", App.RootDir + "Exhibitor/");
			} else {
				intent.putExtra("WebUrl", fileUrls.get(position));
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
		}

	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = null;
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Bundle oArguments = new Bundle();

			if (mFunctionName[position].equals(information.getExhibitorList().getDisplay().en.get(0)) || mFunctionName[position].equals(information.getExhibitorList().getDisplay().sc.get(0))
					|| mFunctionName[position].equals(information.getExhibitorList().getDisplay().tc.get(0))) {

				StatService.onEvent(mContext, EVENTID + "AllExhibitors", "pass", 1);
				intent = new Intent(mContext, ExhibitorListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("Title", mFunctionName[position]);
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}

			} else if (mFunctionName[position].equals(information.getExhibitorList().getDisplay().en.get(1)) || mFunctionName[position].equals(information.getExhibitorList().getDisplay().sc.get(1))
					|| mFunctionName[position].equals(information.getExhibitorList().getDisplay().tc.get(1))) {
				StatService.onEvent(mContext, EVENTID + "SearchByProduct", "pass", 1);
				// SystemMethod.trackLogJson(mContext, 100, "page",
				// "SearchByProduct", null);
				SystemMethod.trackViewLog(mContext, 113, "Page", "SearchByProduct", null, null);
				intent = new Intent(mContext, CategoryListActivity.class);
				intent.putExtra("CategoryType", 1);
				intent.putExtra("Title", mFunctionName[position]);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}

			} else if (mFunctionName[position].equals(information.getExhibitorList().getDisplay().en.get(2))

			|| mFunctionName[position].equals(information.getExhibitorList().getDisplay().sc.get(2)) || mFunctionName[position].equals(information.getExhibitorList().getDisplay().tc.get(2))) {
				StatService.onEvent(mContext, EVENTID + "SearchByHall", "pass", 1);
				SystemMethod.trackViewLog(mContext, 100, "Page", "SearchByHall", null, null);
//				intent = new Intent(mContext, FloorListActivity.class);
//				intent.putExtra("Title", mFunctionName[position]);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
//					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
//				}
			} else if (mFunctionName[position].equals(information.getExhibitorList().getDisplay().en.get(3)) || mFunctionName[position].equals(information.getExhibitorList().getDisplay().sc.get(3))
					|| mFunctionName[position].equals(information.getExhibitorList().getDisplay().tc.get(3))) {
				StatService.onEvent(mContext, EVENTID + "SearchByRegion", "pass", 1);
				SystemMethod.trackViewLog(mContext, 100, "Page", "SearchByRegion", null, null);

				intent = new Intent(mContext, CategoryListActivity.class);
				intent.putExtra("CategoryType", 2);
				intent.putExtra("Title", mFunctionName[position]);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}

			} else if (mFunctionName[position].equals(information.getExhibitorList().getDisplay().en.get(4)) || mFunctionName[position].equals(information.getExhibitorList().getDisplay().sc.get(4))
					|| mFunctionName[position].equals(information.getExhibitorList().getDisplay().tc.get(4))) {
				StatService.onEvent(mContext, EVENTID + "MyExhibitor", "pass", 1);
				SystemMethod.trackViewLog(mContext, 100, "Page", "MyExhibitor", null, null);

				intent = new Intent(mContext, MyExhibitorListActivity.class);
				intent.putExtra("Title", mFunctionName[position]);
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}

		}
	};
	private int mCurrLang;
	private ArrayList<Integer> types;
	private ArrayList<String> itemNames;
	private ArrayList<String> fileUrls;

}
