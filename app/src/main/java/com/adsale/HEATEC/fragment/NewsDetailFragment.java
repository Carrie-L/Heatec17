package com.adsale.HEATEC.fragment;

import java.util.List;

import sanvio.libs.util.DisplayUtils;
import sanvio.libs.util.ImageCache;
import sanvio.libs.view.SanvioImageView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.PadScaleImageActivity;
import com.adsale.HEATEC.activity.ScaleImageActivity;
import com.adsale.HEATEC.activity.WebUrlActivity;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.model.clsNews;
import com.adsale.HEATEC.database.model.clsNewsLink;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;

public class NewsDetailFragment extends BaseFragment {
	public static final String TAG = "NewsDetailFragment";

	private Context mContext;
	private View mBaseView;

	private TextView txtTitle, txtDescription;
	private LinearLayout lyPhoto, lyLink;
	private ImageView imgShare;
	private clsNews moclsNews;
	private String mFilePath, mSharePicPath;
	private String mDownLoadURL;
	private String mTitle;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.f_news_detail, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			moclsNews = savedInstanceState.getParcelable("ClsNews");
		} else {
			moclsNews = ((Activity) mContext).getIntent().getParcelableExtra("ClsNews");
		}
		setupView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable("ClsNews", moclsNews);

	}

	private void findView() {
		txtTitle = (TextView) mBaseView.findViewById(R.id.txtTitle);
		txtDescription = (TextView) mBaseView.findViewById(R.id.txtDescription);
		lyPhoto = (LinearLayout) mBaseView.findViewById(R.id.lyPhoto);
		lyLink = (LinearLayout) mBaseView.findViewById(R.id.lyLink);
		imgShare = (ImageView) mBaseView.findViewById(R.id.imgShare);
	}

	private void setupView() {

		txtTitle.setText(moclsNews.getTitle());
		txtDescription.setText(moclsNews.getDescription());

		ImageCache cache = new ImageCache();
		int imgWidth = DisplayUtils.getScaledValue(300);

		mFilePath = App.RootDir + "News/";
		mDownLoadURL = Configure.DOWNLOAD_PATH + "News/";
		mSharePicPath = "";
		List<clsNewsLink> ocolLinks = moclsNews.getColNewsLinks(mContext);
		if (!ocolLinks.isEmpty()) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			for (clsNewsLink oclsNewsLink : ocolLinks) {
				String strPhoto = oclsNewsLink.getPhoto();
				if (!TextUtils.isEmpty(strPhoto)) {
					SanvioImageView imgPhoto = new SanvioImageView(mContext);
					lyPhoto.addView(imgPhoto, getParams());
					imgPhoto.setImageViewSize(imgWidth, (int) (imgWidth * 0.6));
					imgPhoto.LoadImage(mFilePath, mDownLoadURL, strPhoto, new PhotoClickListener(strPhoto), cache);
					if (TextUtils.isEmpty(mSharePicPath)) {
						mSharePicPath = App.RootDir + "News/" + strPhoto;
					}

				}

				String strLink = oclsNewsLink.getLink();
				if (!TextUtils.isEmpty(strLink)) {
					View convertView = inflater.inflate(R.layout.view_link, lyLink, false);
					TextView txtLink = (TextView) convertView.findViewById(R.id.textView1);
					txtLink.setText(oclsNewsLink.getTitle());
					convertView.setTag(strLink);
					convertView.setOnClickListener(linkClickListener);
					lyLink.addView(convertView, getParams());
				}
			}
		}
		imgShare.setVisibility(View.GONE);
		imgShare.setOnClickListener(mShareClickListener);
	}

	public LinearLayout.LayoutParams getParams() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, DisplayUtils.dip2px(mContext, 10));
		return params;
	}

	class PhotoClickListener implements OnClickListener {
		private String mPhoto;

		public PhotoClickListener(String photo) {
			mPhoto = photo;
		}

		@Override
		public void onClick(View v) {
			Intent intent = null;

			if (!SystemMethod.isPadDevice(mContext)) {
				// SystemMethod.trackLogJson(mContext, 100, "page",
				// "ScaleImageActivity", null);
				SystemMethod.trackViewLog(mContext, 106, "Page", "ScaleImageActivity", null, null);
				intent = new Intent(mContext, ScaleImageActivity.class);
			} else {
				SystemMethod.trackViewLog(mContext, 107, "Page", "PadScaleImageActivity", null, null);

				intent = new Intent(mContext, PadScaleImageActivity.class);
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("Path", mFilePath);
			intent.putExtra("Url", mDownLoadURL);
			intent.putExtra("Img", mPhoto);
			startActivity(intent);
			if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
		}
	};

	private OnClickListener linkClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String url = v.getTag().toString();
			url = url.startsWith("http://") ? url : "http://" + url;
			Intent intent = new Intent(mContext, WebUrlActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("WebUrl", url);
			intent.putExtra("Title", getActivity().getIntent().getStringExtra("Title"));
			mContext.startActivity(intent);
			if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
		}
	};

	@Override
	public void onDestroyView() {
		for (int i = 0; i < lyPhoto.getChildCount() - 1; i++) {
			View oView = lyPhoto.getChildAt(i);
			if (oView instanceof SanvioImageView) {
				SanvioImageView oSanvioImageView = (SanvioImageView) oView;
				oSanvioImageView.DestoryImageView();
			}
		}
		super.onDestroyView();
	}

	OnClickListener mShareClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

}
