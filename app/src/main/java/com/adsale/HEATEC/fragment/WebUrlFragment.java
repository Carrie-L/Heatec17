package com.adsale.HEATEC.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.util.LogUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WebUrlFragment extends BaseFragment {
	protected static final String TAG = "WebUrlFragment";
	private Context mContext;
	private View view;
	private String oDeviceType;
	@ViewInject(R.id.progressBar)
	private ProgressBar progressBar;
	@ViewInject(R.id.webview)
	private WebView webview;
	private SharedPreferences sp;
	private String rootDir;

	private WebSettings mWebSettings;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.f_weburl, null);
		ViewUtils.inject(this, view);
		mContext = getActivity();

		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		Intent intent = ((Activity) mContext).getIntent();
		String url = intent.getStringExtra("WebUrl");

		mWebSettings = webview.getSettings();
		mWebSettings.setSupportZoom(true);
		mWebSettings.setUseWideViewPort(true);
		mWebSettings.setLoadWithOverviewMode(true);
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setDomStorageEnabled(true);
		mWebSettings.setBuiltInZoomControls(true);

		// 去掉缩放按钮
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// Use the API 11+ calls to disable the controls
			mWebSettings.setBuiltInZoomControls(true);
			mWebSettings.setDisplayZoomControls(false);
		}
		webview.loadUrl(url);
		webview.setWebViewClient(new WebViewClient() {
			private String pdfPath;

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				LogUtil.i(TAG, "onPageStarted..." + url);

				if (url.endsWith(".pdf")) {
					pdfPath = App.RootDir + "News/" + url.substring(url.lastIndexOf("/") + 1);
			//		DownloadPDF(url, new File(pdfPath));
					Toast.makeText(mContext, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				LogUtil.i(TAG, "onPageFinished");
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				LogUtil.i(TAG, "shouldOverrideUrlLoading..." + pdfPath);
				view.loadUrl(url);
				return true;
			}
		});

	}

	public static void DownloadPDF(String fileURL, File directory) {
		try {
			FileOutputStream f = new FileOutputStream(directory);
			URL u = new URL(fileURL);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();

			InputStream in = c.getInputStream();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) > 0) {
				f.write(buffer, 0, len1);
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
