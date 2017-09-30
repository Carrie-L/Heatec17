package com.adsale.HEATEC.fragment;

import sanvio.libs.util.FileUtils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.adsale.HEATEC.activity.RegisterActivity;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.database.model.clsMainIcon;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

public class WebContentFragment extends BaseFragment {
    public static final String TAG = "WebContentFragment";

    private Context mContext;
    private View mBaseView;
    public static WebView mWebView;
    private static WebSettings mWebSettings;

    private int mCurLanguage;

    private String mWebUrl;

    private ProgressBar progressBar;

    private boolean mEnableBack = false;
    private boolean mLastEnableBack = false;

    private MainIcon oclsMainIcon;
    private String mDirectoryName;

    private int mPageIndex = 0;

    private String ftpExhiDir;
    private String ftpExhiName;
    private String mTitle;

    private String mDeviceType;

    @Override
    public View initView(LayoutInflater inflater) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.f_webcontent, null);

        findView();

        return mBaseView;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = ((Activity) mContext).getIntent();
        mDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");

        if (savedInstanceState != null) {
            oclsMainIcon = savedInstanceState.getParcelable("clsMainIcon");
            mEnableBack = savedInstanceState.getBoolean("EnableBack", false);
            mWebUrl = savedInstanceState.getString("WebUrl");
            // 从展商列表切换过来
            ftpExhiDir = savedInstanceState.getString("ftpExhiListDir");
            ftpExhiName = savedInstanceState.getString("ftpExhiListName");
        } else {
            oclsMainIcon = intent.getParcelableExtra("clsMainIcon");
            mWebUrl = intent.getStringExtra("WebUrl");
            // 从展商列表切换过来
            ftpExhiDir = intent.getStringExtra("ftpExhiListDir");
            ftpExhiName = intent.getStringExtra("ftpExhiListName");
        }

        setupView();
    }

    private void findView() {
        mWebView = (WebView) mBaseView.findViewById(R.id.webView1);
        progressBar = (ProgressBar) mBaseView.findViewById(R.id.progressBar1);
    }

    private void setupView() {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLightTouchEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebView.setHapticFeedbackEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i(TAG, "url=" + url);

                if (url.startsWith("mailto:")) {
                    try {
                        url = url.replaceFirst("mailto:", "");
                        url = url.trim();
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("plain/text").putExtra(Intent.EXTRA_EMAIL,
                                new String[]{url});
                        mContext.startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext,
                                getString(R.string.exception_toast_email),
                                Toast.LENGTH_LONG).show();
                    }
                    return true;
                } else if (url.startsWith("tel:")) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse(url));
                        mContext.startActivity(callIntent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext,
                                getString(R.string.exception_toast_phone),
                                Toast.LENGTH_LONG).show();
                    }
                    return true;
                } else if (url.startsWith("http")) {
                    if (url.endsWith(".pdf")) {
                        LogUtil.i(TAG, "shouldOverrideUrlLoading .pdf ");
                        try {
                            Uri uriUrl = Uri.parse(url);
                            Intent intentUrl = new Intent(Intent.ACTION_VIEW,
                                    uriUrl);
                            startActivity(intentUrl);
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "No PDF Viewer Installed",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (url.toLowerCase().endsWith(".jpg")
                                || url.toLowerCase().endsWith(".png")) {
                            mWebSettings.setSupportZoom(true);
                            mWebSettings.setUseWideViewPort(true);
                            mWebSettings.setLoadWithOverviewMode(true);
                            // 去掉缩放按钮
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                                // Use the API 11+ calls to disable the controls
                                mWebSettings.setBuiltInZoomControls(true);
                                mWebSettings.setDisplayZoomControls(false);
                            }
                        }
                        App.mPageIndex++;
                        System.out.println(url);
                        mWebView.loadUrl(url);
                    }
                    return true;
                } else if (url.startsWith("web")) {
                    url = url.replace("web://", "http://");
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return true;
                } else if (url.toLowerCase().endsWith(".jpg")
                        || url.toLowerCase().endsWith(".png")) {
                    mWebSettings.setSupportZoom(true);
                    mWebSettings.setUseWideViewPort(true);
                    mWebSettings.setLoadWithOverviewMode(true);
                    // 去掉缩放按钮
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        // Use the API 11+ calls to disable the controls
                        mWebSettings.setBuiltInZoomControls(true);
                        mWebSettings.setDisplayZoomControls(false);
                    }

                    App.mPageIndex++;
                    System.out.println(url);
                    mWebView.loadUrl(url);
                    return true;
                } else if (url.startsWith("prereg://")) {
                    Intent intent = new Intent(mContext, RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Title", getString(R.string.title_register));
                    mContext.startActivity(intent);
                    if (mDeviceType.equals("Pad")) {
                        ((Activity) mContext).overridePendingTransition(
                                R.animator.fade_in, R.animator.fade_out);
                    }
                    return true;
                } else {
                    return false;
                }

            }
        });

        loadWebView();
    }

    private void loadWebView() {
        LogUtil.i(TAG, "loadWebView");
        // 一级界面不允许缩放
        mWebSettings.setSupportZoom(false);
        mWebSettings.setUseWideViewPort(false);
        mWebSettings.setLoadWithOverviewMode(false);

        if (oclsMainIcon != null
                && (mDirectoryName == null || TextUtils.isEmpty(mDirectoryName))) {
            mDirectoryName = oclsMainIcon.getIconID();
        }

        if (!TextUtils.isEmpty(mDirectoryName)) {
            String WebContentDir = App.RootDir
                    + "WebContent/";
            LogUtil.i(TAG, "WebContentDir=" + WebContentDir);

            loadHtml(WebContentDir, mDirectoryName);

        } else if (!TextUtils.isEmpty(mWebUrl)) {
            if (mWebUrl.endsWith(".pdf")) {
                LogUtil.i(TAG, "loadWebView .pdf ");
                try {
                    Uri uriUrl = Uri.parse(mWebUrl);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(intentUrl);
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(mContext, "No PDF Viewer Installed",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                mWebView.loadUrl(mWebUrl);
            }
            System.out.println("mWebUrl===" + mWebUrl);
        } else if (ftpExhiDir != null && ftpExhiName != null) {
            LogUtil.i(TAG, "ftpExhibitor跳转:ftpExhiName=" + ftpExhiName
                    + ",ftpExhiDir=" + ftpExhiDir);
            loadHtml(ftpExhiDir, ftpExhiName);
        } else {
        }

    }

    private void loadHtml(String contentDir, String name) {
        mWebView.loadUrl("file://" + String.format("%1$s%2$s/%3$s", contentDir, name,
                getHtmlName()));
    }

    private String getHtmlName() {
        mCurLanguage = SystemMethod.getCurLanguage(getActivity());
        String htmlName = "TC.htm";
        if (mCurLanguage == 1) {
            htmlName = "EN.htm";
        } else if (mCurLanguage == 2) {
            htmlName = "SC.htm";
        }
        return htmlName;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume");

    }

    public static void goback() {
        App.mPageIndex--;
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            mWebSettings.setSupportZoom(false);
            mWebSettings.setUseWideViewPort(false);
            mWebSettings.setLoadWithOverviewMode(false);
        }
    }

}
