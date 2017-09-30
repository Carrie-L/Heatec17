package com.adsale.HEATEC.fragment;

import java.util.HashMap;
import java.util.List;

import sanvio.libs.dbclass.clsUpdateDateTime;
import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.NoServerException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.NewsDetailActivity;
import com.adsale.HEATEC.adapter.NewsAdapter;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.NewsDBHelper;
import com.adsale.HEATEC.database.NewsLinkDBHelper;
import com.adsale.HEATEC.database.model.clsNews;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class NewsFragment extends BaseFragment {
	public static final String TAG = "NewsFragment";
	public static final String EVENTID = "NewsF_ITEM";

	private Context mContext;
	private View mBaseView;

	private PullToRefreshListView lvNews;
	private NewsAdapter mAdapter;
	private String mTitle;
	private List<clsNews> mColNews;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_news, null);

		mContext = getActivity();

		findView();
	
		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mContext = getActivity();
		setupView();
	}

	private void findView() {
		lvNews = (PullToRefreshListView) mBaseView.findViewById(R.id.lvNews);
	}

	private void setupView() {
		mColNews = new NewsDBHelper(mContext).getNewsList(SystemMethod.getCurLanguage(mContext));
		mAdapter = new NewsAdapter(mContext, mColNews);
		lvNews.setAdapter(mAdapter);
		lvNews.setEmptyView(mBaseView.findViewById(R.id.txtNoData));
		lvNews.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new AnsyRefresh().execute(1);
			}
		});

		lvNews.setOnItemClickListener(oNewItemClickListener);
	}

	private class AnsyRefresh extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... arg0) {
			DatabaseHelper dbHelper = new DatabaseHelper(mContext);
			HashMap<String, clsUpdateDateTime> ocolUpdateDateTime = new HashMap<String, clsUpdateDateTime>();
			setHashMapKey(ocolUpdateDateTime, NewsDBHelper.DBTableBame);
			setHashMapKey(ocolUpdateDateTime, NewsLinkDBHelper.DBTableBame);

			dbHelper.getUpdateDate(ocolUpdateDateTime);

			String pNewsUpdateDateTime = ocolUpdateDateTime.get(NewsDBHelper.DBTableBame).getUpdateDateTime();
			String pNewsLinkUpdateDateTime = ocolUpdateDateTime.get(NewsLinkDBHelper.DBTableBame).getUpdateDateTime();

			try {
				new MasterWSHelper(mContext).getNewsList(pNewsUpdateDateTime, pNewsLinkUpdateDateTime);
			} catch (NoServerException e) {
				LogUtil.e(TAG, "GetMaster Data Error:" + e.getMessage());
			}
			return 1;
		}

		private void setHashMapKey(HashMap<String, clsUpdateDateTime> ocolUpdateDateTime, String pKey) {
			ocolUpdateDateTime.put(pKey, new clsUpdateDateTime(pKey, ""));
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (lvNews.isRefreshing())
				lvNews.onRefreshComplete();

			if (result == 1) {
				mColNews = new NewsDBHelper(mContext).getNewsList(SystemMethod.getCurLanguage(mContext));
				mAdapter = new NewsAdapter(mContext, mColNews);
				lvNews.setAdapter(mAdapter);
			}
		}
	}

	OnItemClickListener oNewItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			StatService.onEvent(mContext, EVENTID, "pass", 1);
			clsNews oClsNews = mAdapter.getItem(position - 1);

			if (oClsNews != null) {
				Intent intent = new Intent(mContext, NewsDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("ClsNews", oClsNews);
				intent.putExtra("Title", getActivity().getIntent().getStringExtra("Title"));// phone
																							// ---
				startActivity(intent);
				if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
					((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
				}
			}
		}

	};

}
