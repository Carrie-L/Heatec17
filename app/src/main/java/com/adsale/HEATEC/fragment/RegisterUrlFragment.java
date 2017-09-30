package com.adsale.HEATEC.fragment;

import java.io.File;

import sanvio.libs.util.DialogUtils;
import sanvio.libs.util.DownLoadUtils;
import sanvio.libs.util.FileUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.App;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.WebContentDBHelper;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;
import com.facebook.drawee.view.SimpleDraweeView;

public class RegisterUrlFragment extends BaseFragment implements
		OnClickListener {

	public static final String TAG = "RegisterUrlFragment";
	private static final int SHOW_IMG = 0;
	protected static final int SHOW_IMG_URL = 1;

	private WebView mWebView;
	private SimpleDraweeView iv_registed;
	private RelativeLayout rl_registed;
	private ScrollView scrollView;
	private Button btn_reRegister;
	private String imgPath;
	private ProgressBar progress_bar;

	protected TextView tv_info1;
	protected TextView tv_info2;
	private SharedPreferences sp;

	private String mRootDir;

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.f_register_url, null);
		mWebView = (WebView) view.findViewById(R.id.webview_register);
		tv_info1 = (TextView) view.findViewById(R.id.tv_register_info1);
		tv_info2 = (TextView) view.findViewById(R.id.tv_register_info2);
		btn_reRegister = (Button) view.findViewById(R.id.btn_reRegister);
		progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
		iv_registed = (SimpleDraweeView) view.findViewById(R.id.iv_registed);
		rl_registed = (RelativeLayout) view.findViewById(R.id.rl_registed);
		scrollView = (ScrollView) view.findViewById(R.id.scroll_view);

		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mRootDir = App.RootDir;
		imgPath = mRootDir + "register.jpg";
		sp=mContext.getSharedPreferences("IsRegister", 0);

		mDBHelper = new WebContentDBHelper(mContext);
		language = SystemMethod.getCurLanguage(mContext);

		btn_reRegister.setOnClickListener(this);

		if (SystemMethod.isRegister(mContext)) {// 已注册
			// 有图片时，直接显示；无图片时，根据 链接,下载图片，并保存到本地
			rl_registed.setVisibility(View.VISIBLE);
			scrollView.setVisibility(View.VISIBLE);
			mWebView.setVisibility(View.INVISIBLE);

			// 从WebContent中获得文字信息
			showRegText();

			if (new File(imgPath).exists()) {
				iv_registed.setImageURI(Uri.fromFile(new File(imgPath)));
			} else {
				//不存在，下载图片
				final String url = sp.getString("RegImageName", "");
				iv_registed.setImageURI(Uri.parse(url));
				new DownloadPicAsync().execute(url);
			}

		} else {
			loadUrl();
		}

	}

	private void loadUrl() {

		mWebView.loadUrl(SystemMethod.getName(
				SystemMethod.getCurLanguage(mContext),
				Configure.Register_TW_URL, Configure.Register_EN_URL,
				Configure.Register_CN_URL));
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl(url);
				System.out.println("shouldOverrideUrlLoading: url=" + url);

				if (url.startsWith("appsave")) {
					rl_registed.setVisibility(View.VISIBLE);
					scrollView.setVisibility(View.VISIBLE);
					mWebView.setVisibility(View.INVISIBLE);
					new DownloadPicAsync().execute(url);
				}

				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				progress_bar.setVisibility(View.GONE);
				super.onPageFinished(view, url);

				System.out.println("onPageFinished: url=" + url);

			}

			@Override
			public void onPageStarted(WebView view, final String url,
					Bitmap favicon) {
				progress_bar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);

				System.out.println("onPageStarted: url=" + url);
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress >= 100) {
					progress_bar.setVisibility(View.GONE);
				} else {
					if (progress_bar.getVisibility() == View.GONE) {
						progress_bar.setVisibility(View.VISIBLE);
					}
					progress_bar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
	}

	/**
	 * 异步下载网页上的图片
	 */
	class DownloadPicAsync extends AsyncTask<String, Integer, Boolean> {

		private Boolean bm = false;
		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			mProgressDialog = DialogUtils.CreateProgressDialog(mContext,
					R.string.Registering_MSg);
			mProgressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {

			try {
				System.out.println("params=====" + params.length + ",,"
						+ params[0]);

				String str = params[0];
				LogUtil.i(TAG, "str=" + str);// appsave://eform.adsale.com.hk/vreg/Files/Mobile/PreReg/453/62301001_ed6730978a.jpg?pd=391&pn=adsale
											// test&pe=dotcom1101@gmail.com

				// String imgUrl=str.replace("appsave", "http");

				String imgUrl = str.substring(
						0,
						str.length()
								- str.substring(str.lastIndexOf("?") - 1)
										.length() + 1).replace("appsave",
						"http");
				LogUtil.i(TAG, "imgUrl=" + imgUrl);

				// RegImageName
				sp.edit().putString("RegImageName", imgUrl).apply();

				String userInfo = str.substring(str.lastIndexOf("?") + 1);
				LogUtil.i(TAG, "userInfo=" + userInfo);

				String visitorId = userInfo.split("&")[0].replace("pd=", "");// 292
				String userName = userInfo.split("&")[1].replace("pn=", "");// adsale
																			// test
				String userEmail = userInfo.split("&")[2].replace("pe=", "");// dotcom1101@gmail.com
				LogUtil.i(TAG, "visitorId=" + visitorId);
				LogUtil.i(TAG, "userName=" + userName);
				LogUtil.i(TAG, "userEmail=" + userEmail);

				System.out.println(TAG + "/rootDir=" + mRootDir);
				bm = DownLoadUtils.downImgByFullPath(imgUrl, mRootDir
						+ "register.jpg");
				System.out.println(TAG + "/url=bm=~~~~~~" + bm);

				SystemMethod.registerIn(mContext);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bm;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.cancel();
			}

			System.out.println("onPostExecute:result=" + result);
			handler.sendEmptyMessage(SHOW_IMG);
		}

	}

	;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_IMG:
				LogUtil.i(TAG, "SHOW_IMG");
				rl_registed.setVisibility(View.VISIBLE);
				mWebView.setVisibility(View.INVISIBLE);
				iv_registed.setImageURI(Uri.fromFile(new File(imgPath)));
				showRegText();
				break;
			case SHOW_IMG_URL:
				iv_registed.setImageURI(Uri.parse((String) msg.obj));
				showRegText();
				break;
			default:
				break;
			}
		}

		;
	};
	private WebContentDBHelper mDBHelper;
	private int language;

	private void showRegText() {
		tv_info1.setText(mDBHelper.getContent(20, language));
		tv_info2.setText(mDBHelper.getContent(21, language));
	}

	@Override
	public void onClick(View v) {
		FileUtils.deleteFile(imgPath);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.reg_reset_title)
				.setPositiveButton(R.string.reg_reset_confirm,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SystemMethod.registerOut(mContext);
								loadUrl();
								scrollView.setVisibility(View.GONE);
								rl_registed.setVisibility(View.GONE);
								mWebView.setVisibility(View.VISIBLE);

							}
						})
				.setNegativeButton(R.string.reg_reset_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create().show();

	}

	public void addToCalendar(View view) {
		SystemMethod.addToCalendar(mContext);
	}

	@Override
	public void onDestroy() {
		mWebView.removeAllViews();
		mWebView = null;
		super.onDestroy();
	}

}
