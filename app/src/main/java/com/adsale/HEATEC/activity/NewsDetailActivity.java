package com.adsale.HEATEC.activity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.dao.News;
import com.adsale.HEATEC.dao.NewsLink;
import com.adsale.HEATEC.dao.NewsLinkDao;
import com.adsale.HEATEC.databinding.ActivityNewsDtlBinding;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.DisplayUtil;
import com.adsale.HEATEC.util.LogUtil;

import java.util.ArrayList;


/**
 * todo track photo显示和点击
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
	public final ObservableField<String> newsTitle = new ObservableField<>();
	public final ObservableField<String> content = new ObservableField<>();
	public final ObservableField<String> logoUrl = new ObservableField<>();
	public final ObservableArrayList<String> links = new ObservableArrayList<>();

	private ActivityNewsDtlBinding binding;
	private News news;
	private NewsLinkDao mLinkDao;

	@Override
	protected void initView() {
		binding = ActivityNewsDtlBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
		binding.setView(this);

		Bundle bundle = getIntent().getExtras();
		news = bundle.getParcelable("News");
		LogUtil.i(TAG, "NEWS= " + news.toString());

		barTitle.set(bundle.getString(Constant.TITLE));

	}

	@Override
	protected void initData() {
		newsTitle.set(news.getTitle());
		content.set(news.getDescription());

		mLinkDao = App.mDBHelper.mLinkDao;
		ArrayList<NewsLink> links = getLinks(news.getNewsID());

		LinearLayout linkLayout = binding.lyLink;
		int size = links.size();
		LayoutInflater inflater = getLayoutInflater();
		if (!links.isEmpty()) {
			for (NewsLink oNewsLink : links) {
//                strPhoto = oNewsLink.getPhoto();
//                if (!TextUtils.isEmpty(strPhoto)) {
//                    photoUrl = mDownLoadURL + strPhoto;
//                    LogUtil.i(TAG, "photoUrl=" + photoUrl);
//                    imgPath = App.rootDir + "News/" + strPhoto;
//                    LogUtil.i(TAG, "strPhoto=" + strPhoto);
//                }

				String strLink = oNewsLink.getLink();
				if (!TextUtils.isEmpty(strLink)) {
					View linkView = inflater.inflate(R.layout.view_link, linkLayout, false);
					TextView txtLink = (TextView) linkView.findViewById(R.id.textView1);
					txtLink.setText(oNewsLink.getTitle());
					linkView.setTag(oNewsLink.getLink());
					linkView.setOnClickListener(this);
					linkLayout.addView(linkView, getParams());
				}
			}
		}
	}

	public LinearLayout.LayoutParams getParams() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, DisplayUtil.dip2px(getApplicationContext(), 10));
		return params;
	}

	@Override
	public void onClick(View v) {
		String url = v.getTag().toString();
//        SystemMethod.trackViewLog(mContext, 190, "Page", newsID, "NewsLink");



		LogUtil.i(TAG,"url="+url);
	}

	/**
	 * select  * from NEWS_LINK NL,NEWS N where NL.NEWS_ID=N.NEWS_ID AND N.NEWS_ID="0000000668"
	 */
	public ArrayList<NewsLink> getLinks(String newsId) {
		return (ArrayList<NewsLink>) mLinkDao.queryBuilder()
				.where(NewsLinkDao.Properties.NewsID.eq(newsId))
				.orderAsc(NewsLinkDao.Properties.SEQ).list();
	}
}
